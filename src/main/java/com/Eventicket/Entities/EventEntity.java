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
import java.util.Set;
import java.util.List;

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

    // associação evento endereço
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    @JsonIgnoreProperties("eventos")
    private AddresEntity endereco;

    // associação evento ingresso
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<TicketEntity> ingresso;

    // associcação categoria e evento
    @ManyToMany
    @JoinTable(
            name = "event_category",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnoreProperties("events")
    private Set<CategoryEntity> categories;
}
