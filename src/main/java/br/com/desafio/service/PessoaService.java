package br.com.desafio.service;

import br.com.desafio.domain.Pessoa;
import br.com.desafio.domain.TipoIdentificador;
import br.com.desafio.domain.record.PessoaRequest;
import br.com.desafio.domain.record.PessoaResponse;
import br.com.desafio.excpetions.InvalidRequestException;
import br.com.desafio.excpetions.NumberNotValidExpetion;
import br.com.desafio.excpetions.PessoaNaoExistenteException;
import br.com.desafio.repository.PessoaRepository;
import br.com.desafio.utils.ValidadorDeIdentificadores;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Transactional
    public PessoaResponse createPessoa(PessoaRequest pessoaRequest) {

        if (isNull(pessoaRequest) || pessoaRequest.identificador() == null) {
            throw new InvalidRequestException();
        }

        validaIdentificador(pessoaRequest);
        var pessoaNotValid = pessoaRepository.findByIdentificador(pessoaRequest.identificador());
        if(pessoaNotValid.isPresent()){
            throw  new PessoaNaoExistenteException();
        }
        var tipoIdentificador = IdentificadorService.tipoIdentificador(pessoaRequest.identificador());
        var request = validaPessoaTipoIdentificador(pessoaRequest, tipoIdentificador);
        var pessoa = request.toEntity();
        pessoaRepository.save(pessoa);
        log.info("Pessoa cadastrada com sucesso: Pessoa ID: {}", pessoa.getId());

        return PessoaResponse.fromResponse(pessoa);
    }

    public PessoaRequest validaPessoaTipoIdentificador(PessoaRequest request, TipoIdentificador tipoIdentificadorValidado) {
        var max = BigDecimal.ZERO;
        var mim = BigDecimal.ZERO;

        switch (requireNonNull(tipoIdentificadorValidado)) {
            case AP -> {
                mim = BigDecimal.valueOf(400);
                max = BigDecimal.valueOf(25000);
            }
            case EU -> {
                mim = BigDecimal.valueOf(100);
                max = BigDecimal.valueOf(10000);
            }
            case PF -> {
                mim = BigDecimal.valueOf(300);
                max = BigDecimal.valueOf(10000);
            }
            case PJ -> {
                mim = BigDecimal.valueOf(1000);
                max = BigDecimal.valueOf(100000);
            }
        }

        return PessoaRequest.builder().nome(request.nome()).identificador(request.identificador()).dataNascimento(request.dataNascimento()).tipoIdentificador(tipoIdentificadorValidado).mim(mim).max(max).build();
    }

    public PessoaResponse findPessoa(Integer id) {
        return pessoaRepository
                .findById(id)
                .map(PessoaResponse::fromResponse)
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<PessoaResponse> findAll() {
        return pessoaRepository
                .findAll()
                .stream()
                .map(PessoaResponse::fromResponse)
                .toList();
    }

    public void deletePessoa(Integer id) {
        var pessoa = pessoaRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        pessoaRepository.delete(pessoa);
    }

    public PessoaResponse updatePessoa(Integer id, PessoaRequest pessoaRequest) {
        if (Objects.isNull(pessoaRequest)) {
            throw new InvalidRequestException();
        }

        var pessoaExistente = pessoaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pessoa not found with id: " + id));

        var pessoaAtualizada = atualizaDadosPessoa(pessoaExistente, pessoaRequest);
        pessoaAtualizada.setId(pessoaExistente.getId());

        pessoaRepository.save(pessoaAtualizada);

        return PessoaResponse.fromResponse(pessoaAtualizada);
    }

    private Pessoa atualizaDadosPessoa(Pessoa pessoa, PessoaRequest pessoaRequest) {
        return PessoaRequest.builder()
                .nome(pessoaRequest.nome())
                .identificador(pessoaRequest.identificador())
                .dataNascimento(pessoaRequest.dataNascimento())
                .tipoIdentificador(pessoaRequest.tipoIdentificador())
                .max(pessoaRequest.max())
                .mim(pessoaRequest.mim())
                .build().toEntity();
    }

    private void validaIdentificador(PessoaRequest pessoaRequest) {
        var tipoIdentificador = pessoaRequest.tipoIdentificador();
        var identification = pessoaRequest.identificador();

        switch (tipoIdentificador) {
            case AP -> ValidadorDeIdentificadores.validarAposentado(identification);
            case EU -> ValidadorDeIdentificadores.validarEstudante(identification);
            case PF -> ValidadorDeIdentificadores.validarCPF(identification);
            case PJ -> ValidadorDeIdentificadores.validarCNPJ(identification);
            default -> throw new NumberNotValidExpetion();
        }
    }
}
