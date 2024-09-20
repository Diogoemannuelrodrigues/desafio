package br.com.desafio.domain.record;

import br.com.desafio.domain.Pessoa;
import br.com.desafio.domain.TipoIdentificador;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PessoaResponse(Integer id,
                             String nome,
                             String identificador,
                             LocalDate dataNascimento,
                             TipoIdentificador tipoIdentificador,
                             BigDecimal valorMinimoParcela,
                             BigDecimal valorMaximoParcela) {

    public static PessoaResponse fromResponse(Pessoa pessoa) {
        return new PessoaResponse(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getIdentificador(),
                pessoa.getDataNascimento(),
                pessoa.getTipoIdentificador(),
                pessoa.getValorMinimoParcela(),
                pessoa.getValorMaximoParcela());
    }
}
