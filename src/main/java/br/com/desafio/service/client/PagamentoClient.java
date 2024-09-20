package br.com.desafio.service.client;

import br.com.desafio.domain.record.EmprestimoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "pagamento", url = "${pagamento.url}")
public interface PagamentoClient {

    @PostMapping("/pagar/{id}")
    EmprestimoResponse realizarPagamento(@PathVariable Integer id);
}
