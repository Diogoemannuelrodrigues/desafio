package br.com.desafio.domain;

import static java.util.Objects.isNull;

public enum TipoIdentificador {

    PF(11),
    PJ(14),
    EU(8),
    AP(10);

    private int tipo;

    private TipoIdentificador(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public static TipoIdentificador tipoIdentificador(String tipo) {

        if(isNull(tipo) || !tipo.matches("\\d+")){
            return null;
        }

        for (TipoIdentificador tipoIdentificador : TipoIdentificador.values()) {
            if (tipoIdentificador.getTipo() == tipo.length()) {
                return tipoIdentificador;
            }
        }

        throw new IllegalArgumentException("Id inválido: ");
    }

}
