package com.Eventicket.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "event")
@Table(name = "event")
@JsonIgnoreProperties({"ingressos"})
public class EventEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$", message = "O nome deve conter apenas letras e espaços.")
    private String nome;

    @NotNull
    private Double precoDoIngresso;

    @NotNull
    private Integer quantidade;

    @NotNull
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDate data;

    @NotNull
    @NotBlank
    @NotEmpty
    //@Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ0-9 .,;!?'\"-]+$", message = "A descrição pode conter letras, números, espaços e caracteres de pontuação básicos.")
    private String descricao;

    // associação evento endereço
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    @JsonIgnoreProperties("eventos")
    private AddresEntity endereco;

    // associcação categoria e evento
    @ManyToMany
    @JoinTable(
            name = "event_category",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnoreProperties("events")
    private Set<CategoryEntity> categories;

    @OneToMany(mappedBy = "evento", fetch = FetchType.EAGER)
    private List<TicketEntity> ingressos;
}