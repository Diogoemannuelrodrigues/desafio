package br.com.desafio.service;

import br.com.desafio.domain.Emprestimo;
import br.com.desafio.domain.Pessoa;
import br.com.desafio.domain.StatusPagamento;
import br.com.desafio.domain.TipoIdentificador;
import br.com.desafio.domain.record.EmprestimoRequest;
import br.com.desafio.domain.record.EmprestimoResponse;
import br.com.desafio.domain.record.PessoaResponse;
import br.com.desafio.repository.EmprestimoRepository;
import br.com.desafio.repository.PessoaRepository;
import br.com.desafio.service.client.PagamentoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmprestimoServiceTest {

    @InjectMocks
    private EmprestimoService emprestimoService;

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private PagamentoClient pagamentoClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEmprestimo() {
        var pessoa = getPessoaEntity();
        var pessoaResponse = PessoaResponse.fromResponse(pessoa);

        var emprestimoRequest = EmprestimoRequest.builder()
                .pessoaResponse(pessoaResponse)
                .numeroParcelas(12)
                .valorEmprestimo(BigDecimal.valueOf(1000))
                .build();
        var emprestimoEntity = getEmprestimoEntyti();

        when(pessoaRepository.findByIdentificador("12345678901")).thenReturn(Optional.of(pessoa));
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(emprestimoEntity);
        when(pagamentoClient.realizarPagamento(anyInt())).thenReturn(EmprestimoResponse.fromEntity(emprestimoEntity));

        EmprestimoResponse response = emprestimoService.criarEmprestimo(emprestimoRequest);

        assertNotNull(response);
        assertEquals(emprestimoEntity.getId(), response.id());

        verify(emprestimoRepository).save(any(Emprestimo.class));
        verify(pessoaRepository).findByIdentificador("12345678901");
        verify(pagamentoClient, times(1)).realizarPagamento(anyInt());

    }


    private Emprestimo getEmprestimoEntyti() {
        return Emprestimo.builder()
                .pessoa(getPessoaEntity())
                .valorEmprestimo(BigDecimal.valueOf(1000))
                .numeroParcelas(12)
                .id(1)
                .dataCriacao(LocalDate.now())
                .statusPagamento(StatusPagamento.EMPROCESSAMENTO)
                .build();
    }

    private static Pessoa getPessoaEntity() {
        return Pessoa.builder().id(1).nome("João da Silva").identificador("12345678901").dataNascimento(LocalDate.of(1990, 1, 1)).tipoIdentificador(TipoIdentificador.PF).build();
    }



}
