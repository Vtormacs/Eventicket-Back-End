package com.Eventicket.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull@NotEmpty@NotBlank
    private String nome;

    @NotNull@NotEmpty@NotBlank
    private String cpf;

    @NotNull@NotEmpty@NotBlank
    private String email;

    @NotNull@NotEmpty@NotBlank
    private String senha;

    @NotNull@NotEmpty@NotBlank
    private String celular;

    // TODO associações com Endereço
}
