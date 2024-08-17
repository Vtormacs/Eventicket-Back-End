package com.Eventicket.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

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

    @NotNull
    @NotEmpty
    @NotBlank
    private String nome;

    @NotNull
    @NotEmpty
    @NotBlank
    @Column(unique = true)
    private String cpf;

    @NotNull
    @NotEmpty
    @NotBlank
    private String email;

    @NotNull
    @NotEmpty
    @NotBlank
    private String senha;

    private String celular;

    @ManyToOne(cascade = CascadeType.ALL) // Cascata para salvar o endereço automaticamente
    @JoinColumn(name = "endereco_id")
    @JsonIgnoreProperties("usuarios")
    private AddresEntity endereco;
}
