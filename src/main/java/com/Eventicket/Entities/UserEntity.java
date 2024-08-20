package com.Eventicket.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(max = 100, message = "O nome não pode ter mais de 100 caracteres.")
    private String nome;

    @NotNull
    @NotEmpty
    @NotBlank
    @CPF(message = "O CPF deve ser válido.")
    @Column(unique = true)
    private String cpf;

    @NotNull
    @NotEmpty
    @NotBlank
    @Email(message = "O email deve ser válido.")
    @Size(max = 255, message = "O email não pode ter mais de 255 caracteres.")
    private String email;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    private String senha;

    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Número de celular inválido")
    private String celular;

    @ManyToOne(cascade = CascadeType.ALL) // Cascata para salvar o endereço automaticamente
    @JoinColumn(name = "endereco_id")
    @JsonIgnoreProperties("usuarios")
    private AddresEntity endereco;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnoreProperties("usuario")
    private List<BuyEntity> compras;
}
