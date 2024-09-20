package br.com.desafio.domain.record;

import br.com.desafio.domain.Emprestimo;
import br.com.desafio.domain.StatusPagamento;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record EmprestimoRequest(Integer id,
        PessoaResponse pessoaResponse,
                                Integer numeroParcelas,
                                BigDecimal valorEmprestimo,
                                StatusPagamento statusPagamento,
                                LocalDate dataCriacao) {

    public static EmprestimoResponse fromEntity(Emprestimo emprestimo) {
        return new EmprestimoResponse(
                emprestimo.getId(),
                PessoaResponse.fromResponse(emprestimo.getPessoa()),
                emprestimo.getNumeroParcelas(),
                emprestimo.getValorEmprestimo(),
                emprestimo.getStatusPagamento(),
                emprestimo.getValorDaParcela(),
                emprestimo.getDataCriacao()
        );
    }
}