package com.Eventicket.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "addres")
public class AddresEntity {

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

    @OneToMany(mappedBy = "endereco")
    @JsonIgnoreProperties("endereco")
    private List<UserEntity> usuarios;

}
