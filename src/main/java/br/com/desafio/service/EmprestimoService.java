package br.com.desafio.service;

import br.com.desafio.domain.Emprestimo;
import br.com.desafio.domain.Pessoa;
import br.com.desafio.domain.StatusPagamento;
import br.com.desafio.domain.record.EmprestimoRequest;
import br.com.desafio.domain.record.EmprestimoResponse;
import br.com.desafio.excpetions.EmprestimoNotFoundException;
import br.com.desafio.excpetions.PessoaNaoExistenteException;
import br.com.desafio.excpetions.QuantidadeDeParcelaExpetion;
import br.com.desafio.repository.EmprestimoRepository;
import br.com.desafio.repository.PessoaRepository;
import br.com.desafio.service.client.PagamentoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EmprestimoService {

    private static final Integer NUMEROPARCELA = 24;

    private final PessoaRepository pessoaRepository;
    private final EmprestimoRepository emprestimoRepository;
    private final PagamentoClient pagamentoClient;

    public EmprestimoResponse criarEmprestimo(EmprestimoRequest request) {
        // Cria o novo empréstimo com os dados validados
        Emprestimo emprestimo = new Emprestimo();
        var pessoa = pessoaRepository
                .findByIdentificador(request.pessoaResponse().identificador())
                .orElseThrow(PessoaNaoExistenteException::new);

        this.validaLimiteMaximoEmprestimo(request, pessoa);
        emprestimo.setValorDaParcela(this.validaQuantidadeMinimoParcela(request));
        this.validaQuantidadeParcela(request);

        emprestimo.setPessoa(pessoa);
        emprestimo.setNumeroParcelas(request.numeroParcelas());
        emprestimo.setValorEmprestimo(request.valorEmprestimo());
        emprestimo.setStatusPagamento(StatusPagamento.EMPROCESSAMENTO);
        emprestimo.setDataCriacao(LocalDate.now());

        var emrpestimo = emprestimoRepository.save(emprestimo);

        //adicionar rabbitMq para
        pagamentoClient.realizarPagamento(emprestimo.getId());

        return EmprestimoResponse.fromEntity(emrpestimo);
    }




    private void validaQuantidadeParcela(EmprestimoRequest request) {
        if (request.numeroParcelas() > NUMEROPARCELA) {
            throw new QuantidadeDeParcelaExpetion();
        }
    }

    private BigDecimal validaQuantidadeMinimoParcela(EmprestimoRequest request) {

        var valorMinimoparcelado = request.pessoaResponse().valorMinimoParcela();
        var valorDoEmprestimo = request.valorEmprestimo();

        if (valorMinimoparcelado == null || valorDoEmprestimo == null) {
            throw new IllegalArgumentException("Valor maximo da parcela ou do emprestimo nao podem ser nulos");
        }
        var valorParcelado = valorDoEmprestimo.divide(new BigDecimal(request.numeroParcelas()), RoundingMode.HALF_EVEN);

        if(valorParcelado.compareTo(valorMinimoparcelado) > 0){
            throw new IllegalArgumentException("O valor do empréstimo e menor que o minimo parcelado.");
        }

        return valorParcelado;
    }


    private void validaLimiteMaximoEmprestimo(EmprestimoRequest request, Pessoa pessoa) {
        var valorMaximo = pessoa.getValorMaximoParcela();
        var valorEmprestimo = request.valorEmprestimo();

        if (valorMaximo == null || valorEmprestimo == null) {
            throw new IllegalArgumentException("Valor maximo da parcela ou do emprestimo nao podem ser nulos ou zerados");
        }

        if (valorEmprestimo.compareTo(valorMaximo) > 0) {
            throw new IllegalArgumentException("O valor do empréstimo excede o limite máximo disponível.");
        }
    }

    public List<EmprestimoResponse> buscarEmprestimos() {
        return emprestimoRepository
                .findAll()
                .stream()
                .map(EmprestimoResponse::fromEntity)
                .toList();
    }

    public EmprestimoResponse buscarEmprestimoById(Integer id) {
        return emprestimoRepository
                .findById(id)
                .map(EmprestimoResponse::fromEntity)
                .orElseThrow(EmprestimoNotFoundException::new);
    }


}
