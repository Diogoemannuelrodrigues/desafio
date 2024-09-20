package br.com.desafio.excpetions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.time.LocalDate;

public class EmprestimoNotFoundException extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        pb.setTitle("Emprestimo nao encontrado");
        pb.setProperty("timesTamp", LocalDate.now());

        return pb;
    }
}