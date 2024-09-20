package br.com.desafio.domain.record;

import br.com.desafio.domain.Pessoa;
import br.com.desafio.domain.TipoIdentificador;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record PessoaRequest (String nome,
                            String identificador,
                            LocalDate dataNascimento,
                            TipoIdentificador tipoIdentificador,
                            BigDecimal mim,
                            BigDecimal max){

    public Pessoa toEntity(){
        return Pessoa.builder()
                .nome(nome)
                .identificador(identificador)
                .dataNascimento(dataNascimento)
                .tipoIdentificador(tipoIdentificador)
                .valorMinimoParcela(mim)
                .valorMaximoParcela(max)
                .build();
    }
}

