package com.Eventicket.Entities;

import com.Eventicket.Entities.Enum.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Table(name = "users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotBlank
    @Size(max = 100, message = "O nome não pode ter mais de 100 caracteres.")
    private String nome;

    @NotBlank
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$", message = "O CPF deve estar no formato XXX.XXX.XXX-XX")
    @Column(unique = true)
    private String cpf;

    @NotBlank
    @Email(message = "O email deve ser válido.")
    @Size(max = 255, message = "O email não pode ter mais de 255 caracteres.")
    private String email;

    @NotBlank
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    private String senha;

    @NotBlank
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Número de celular inválido")
    private String celular;

    Boolean ativo = false;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    @JsonIgnoreProperties("usuarios")
    private AddresEntity endereco;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnoreProperties({"ingressos", "usuario"})
    private List<BuyEntity> compras = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    @JsonIgnoreProperties({"usuario", "quantidade"})
    private List<TicketEntity> ingressos = new ArrayList<>();


    public UserEntity(String nome, String email, String senha, Role role, String cpf, String celular) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.role = role;
        this.celular = celular;
        this.cpf = cpf;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        if (this.role == Role.ADMIN) {
//            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_ORGANIZADOR"), new SimpleGrantedAuthority("ROLE_CLIENTE"));
//        }
//        if (this.role == Role.ORGANIZADOR) {
//            return List.of(new SimpleGrantedAuthority("ROLE_ORGANIZADOR"), new SimpleGrantedAuthority("ROLE_CLIENTE"));
//        } else return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
//    }
//
//    @Override
//    public String getPassword() {
//        return senha;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
}
