package br.com.desafio.excpetions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InvalidRequestException extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        pb.setTitle("Campo invalido ou pessoa nula");

        return pb;
    }
}


