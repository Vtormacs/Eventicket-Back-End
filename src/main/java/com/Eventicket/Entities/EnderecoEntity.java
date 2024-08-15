package com.Eventicket.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Endereco")
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String estado;

    @NotNull
    @NotBlank
    @NotEmpty
    private String cidade;

    @NotNull
    @NotBlank
    @NotEmpty
    private String rua;

    @NotNull
    @NotBlank
    @NotEmpty
    private String numero;
}
