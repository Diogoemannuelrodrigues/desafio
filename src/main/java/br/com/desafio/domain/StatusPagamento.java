package br.com.desafio.domain;

import lombok.Getter;

@Getter
public enum StatusPagamento {
    EMPROCESSAMENTO(1, "EMPROCESSAMENTO"), PAGO(2, "PAGO");

    private final int cod;
    private final String descricao;

    private StatusPagamento(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static StatusPagamento toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (StatusPagamento x : StatusPagamento.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }

}