package com.Eventicket.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "event")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String nome;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date data;

    @NotNull
    @NotBlank
    @NotEmpty
    private String descricao;

//Fazer associção com Ingresso e Endereço
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    @JsonIgnoreProperties("eventos")
    private AddresEntity endereco;

}
