package br.com.desafio.excpetions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DesafioExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ProblemDetail handleInvalidRequestException(InvalidRequestException e) {
        log.error("Invalid request: {}", e.getMessage(), e);
        return e.toProblemDetail();
    }

    @ExceptionHandler(PessoaNaoExistenteException.class)
    public ProblemDetail handlePessoaNaoExistenteException(PessoaNaoExistenteException e) {
        log.error("Pessoa não existente: {}", e.getMessage(), e);
        return e.toProblemDetail();
    }

    @ExceptionHandler(EmprestimoNotFoundException.class)
    public ProblemDetail emprestimoNotFoundException(EmprestimoNotFoundException e) {
        log.error("Emprestimo não existente: {}", e.getMessage(), e);
        return e.toProblemDetail();
    }

    @ExceptionHandler(NumberNotValidExpetion.class)
    public ProblemDetail numberNotValidExpetion(NumberNotValidExpetion e) {
        log.error("CPF ou CNPJ Invalido: {}", e.getMessage(), e);
        return e.toProblemDetail();
    }

    @ExceptionHandler(QuantidadeDeParcelaExpetion.class)
    public ProblemDetail quantidadeDeParcelaExpetion(QuantidadeDeParcelaExpetion e) {
        log.error("Excedeu o valor de parcelas: {}", e.getMessage(), e);
        return e.toProblemDetail();
    }

    @ExceptionHandler(CNPJException.class)
    public ProblemDetail cNPJException(CNPJException e) {
        log.error("CNPJ invalido: {}", e.getMessage(), e);
        return e.toProblemDetail();
    }

    @ExceptionHandler(CPFException.class)
    public ProblemDetail cPFException(CPFException e) {
        log.error("CPF invalido: {}", e.getMessage(), e);
        return e.toProblemDetail();
    }

    @ExceptionHandler(TipoIdentificadorException.class)
    public ProblemDetail tipoIdentificadorException(TipoIdentificadorException e) {
        log.error("Tipo identificador Exception: {}", e.getMessage(), e);
        return e.toProblemDetail();
    }


}
