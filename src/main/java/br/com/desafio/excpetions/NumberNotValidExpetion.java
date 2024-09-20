package br.com.desafio.excpetions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.time.LocalDateTime;

public class NumberNotValidExpetion extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        pb.setTitle("CPF ou CNPJ Invalido");
        pb.setProperty("timeStamp", LocalDateTime.now());

        return pb;
    }
}
