package br.com.desafio.controller;

import br.com.desafio.domain.record.PessoaRequest;
import br.com.desafio.domain.record.PessoaResponse;
import br.com.desafio.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<PessoaResponse> salvarPessoa(@RequestBody PessoaRequest pessoaRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.createPessoa(pessoaRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> buscarPessoa(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findPessoa(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<PessoaResponse>> buscarTodasPessoas(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponse> atualizarPessoa(@PathVariable Integer id, @RequestBody PessoaRequest pessoaRequest) {
        PessoaResponse pessoaResponse = pessoaService.updatePessoa(id, pessoaRequest);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarPessoa(@PathVariable Integer id) {
        pessoaService.deletePessoa(id);
        return ResponseEntity.noContent().build();
    }
}
