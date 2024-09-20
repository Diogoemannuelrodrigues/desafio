package br.com.desafio.excpetions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.time.LocalDateTime;

public class PessoaNaoExistenteException  extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        pb.setTitle("A pessoa informada não existe ou já está registrada na base de dados");
        pb.setProperty("timeStamp", LocalDateTime.now());

        return pb;
    }
}