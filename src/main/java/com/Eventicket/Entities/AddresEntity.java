package com.Eventicket.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "addres")
@Table(name = "addres")
public class AddresEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$", message = "O estado deve conter apenas letras e espaços.")
    private String estado;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$", message = "A cidade deve conter apenas letras e espaços.")
    private String cidade;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ0-9 ]+$", message = "A rua deve conter apenas letras, números e espaços.")
    private String rua;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^\\d+[A-Za-z-]?\\d*$", message = "O número deve ser válido.")
    private String numero;

    @OneToMany(mappedBy = "endereco")
    @JsonIgnore
    private List<UserEntity> usuarios;

    @OneToMany(mappedBy = "endereco")
    @JsonIgnore
    private List<EventEntity> eventos;
}
