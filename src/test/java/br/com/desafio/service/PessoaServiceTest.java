package br.com.desafio.service;

import br.com.desafio.domain.Pessoa;
import br.com.desafio.domain.TipoIdentificador;
import br.com.desafio.domain.record.PessoaRequest;
import br.com.desafio.domain.record.PessoaResponse;
import br.com.desafio.excpetions.PessoaNaoExistenteException;
import br.com.desafio.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;
    @InjectMocks
    private PessoaService pessoaService;

    @Test
    void criaPessoa() {

        var pessoaRequest = getPessoaRequest();

        var pessoaEntity = getPessoaEntity();

        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoaEntity);

        PessoaResponse response = pessoaService.createPessoa(pessoaRequest);

        assertEquals(response.nome(), pessoaRequest.nome());
        assertEquals(response.identificador(), pessoaRequest.identificador());
        verify(pessoaRepository).save(any(Pessoa.class));

    }

    @Test
    void createPessoa_lanceException() {
        var pessoaRequest = getPessoaRequest();
        when(pessoaRepository.findByIdentificador(pessoaRequest.identificador())).thenReturn(Optional.of(new Pessoa()));
        assertThrows(PessoaNaoExistenteException.class, () -> pessoaService.createPessoa(pessoaRequest));
    }

    @Test
    void findPessoa(){
        var pessoa = getPessoaEntity();
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));

        var response = pessoaService.findPessoa(1);

        assertNotNull(response);
        assertEquals("João da Silva", response.nome());
    }

    @Test
    void deletePessoa() {
        var pessoaEntity = getPessoaEntity();

        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoaEntity));
        pessoaService.deletePessoa(1);

        verify(pessoaRepository).delete(pessoaEntity);
    }

    private static Pessoa getPessoaEntity() {
        return Pessoa.builder()
                .nome("João da Silva")
                .identificador("12345678901")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .tipoIdentificador(TipoIdentificador.PF)
                .build();
    }

    private static PessoaRequest getPessoaRequest() {
        return PessoaRequest.builder()
                .nome("João da Silva")
                .identificador("12345678901")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .tipoIdentificador(TipoIdentificador.PF)
                .max(new BigDecimal(1000))
                .mim(new BigDecimal(300))
                .build();
    }


}
