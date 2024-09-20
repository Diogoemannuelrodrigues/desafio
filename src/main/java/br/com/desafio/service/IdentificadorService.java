package br.com.desafio.service;

import br.com.desafio.domain.TipoIdentificador;
import br.com.desafio.excpetions.TipoIdentificadorException;
import org.springframework.stereotype.Service;

@Service
public class IdentificadorService {

    public static TipoIdentificador tipoIdentificador(String identificador) {
        if (identificador == null) {
            throw new IllegalArgumentException("O identificador não pode ser nulo.");
        }

        identificador = identificador.replaceAll("\\D", "");

        int tamanho = identificador.length();

        return switch (tamanho) {
            case 8 -> TipoIdentificador.EU;
            case 10 -> TipoIdentificador.AP;
            case 11 -> TipoIdentificador.PF;
            case 14 -> TipoIdentificador.PJ;
            default -> throw new TipoIdentificadorException();
        };
    }

}
