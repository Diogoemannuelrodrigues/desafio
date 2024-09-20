package br.com.desafio.utils;

import br.com.desafio.excpetions.CNPJException;
import br.com.desafio.excpetions.CPFException;
import org.springframework.stereotype.Service;

@Service
public class ValidadorDeIdentificadores {


    public static void validarCPF(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            return;
        }

        int soma = 0, digito1, digito2;

        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        digito1 = 11 - (soma % 11);
        digito1 = (digito1 >= 10) ? 0 : digito1;

        if (digito1 != Character.getNumericValue(cpf.charAt(9))) {
            throw new CPFException();
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        digito2 = 11 - (soma % 11);
        digito2 = (digito2 >= 10) ? 0 : digito2;

        Character.getNumericValue(cpf.charAt(10));
    }

    public static void validarCNPJ(String cnpj) {
        if (cnpj == null || !cnpj.matches("\\d{14}")) {
            throw new CNPJException();
        }

        int soma = 0, digito1, digito2;

        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 12; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos1[i];
        }
        digito1 = 11 - (soma % 11);
        digito1 = (digito1 >= 10) ? 0 : digito1;

        if (digito1 != Character.getNumericValue(cnpj.charAt(12))) {
            throw new CNPJException();
        }

        soma = 0;
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 13; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos2[i];
        }
        digito2 = 11 - (soma % 11);
        digito2 = (digito2 >= 10) ? 0 : digito2;

//        return digito2 == Character.getNumericValue(cnpj.charAt(13));
    }


    public static void validarEstudante(String identificador) {
        if (identificador.length() != 8 || (Character.getNumericValue(identificador.charAt(0)) +
                Character.getNumericValue(identificador.charAt(7)) != 9)) {
            throw new IllegalArgumentException("Identificador de Estudante inválido.");
        }
    }

    public static void validarAposentado(String identificador) {
        if (identificador.length() != 10 || containsDigit(identificador.charAt(9), identificador.substring(0, 9))) {
            throw new IllegalArgumentException("Identificador de Aposentado inválido.");
        }
    }
    private static boolean containsDigit(char digit, String str) {
        return str.indexOf(digit) >= 0;
    }


}
