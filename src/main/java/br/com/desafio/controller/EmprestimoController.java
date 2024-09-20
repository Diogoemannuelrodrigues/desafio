package br.com.desafio.controller;

import br.com.desafio.domain.record.EmprestimoRequest;
import br.com.desafio.domain.record.EmprestimoResponse;
import br.com.desafio.service.EmprestimoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/emprestimo")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @PostMapping
    public ResponseEntity<EmprestimoResponse> fazerEmprestimo(@Valid @RequestBody EmprestimoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.emprestimoService.criarEmprestimo(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoResponse> buscarEmprestimoPorId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.buscarEmprestimoById(id));
    }

    @GetMapping("/emprestimos")
    public ResponseEntity<List<EmprestimoResponse>> buscarEmprestimos() {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.buscarEmprestimos());
    }


}
