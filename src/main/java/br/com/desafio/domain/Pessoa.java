package br.com.desafio.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "pessoa")
@NoArgsConstructor
@JsonIgnoreProperties
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Size(min = 8, max = 14)
    @Column(name = "identificador", nullable = false)
    private String identificador;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "tipo_identificador", nullable = false)
    private TipoIdentificador tipoIdentificador;

    @Column(name = "valor_maximo_parcela", nullable = false)
    private BigDecimal valorMaximoParcela;

    @Column(name = "valor_minimo_parcela", nullable = false)
    private BigDecimal valorMinimoParcela;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Emprestimo> emprestimos;

}
