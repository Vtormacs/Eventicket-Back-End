package com.Eventicket.Entities;

import com.Eventicket.Entities.Enums.CategoryTicket;
import com.Eventicket.Entities.Enums.StatusTicket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ticket")
@Table(name = "ticket")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double preco;
    
    ///EnumType.STRING: Isso especifica que o valor da enumeração será armazenado como uma string no banco de dados, em vez de um número inteiro.
    //@Enumerated: Essa anotação é usada para mapear o campo da enumeração statusTicket para uma coluna no banco de dados.

    @Enumerated(EnumType.STRING)
    private StatusTicket statusTicket;

    @Enumerated(EnumType.STRING)
    private CategoryTicket categoryTicket;

    // associação ticket evento
    @ManyToOne
    @JoinColumn(name = "evento_id")
    @JsonIgnoreProperties("ingresso")
    private EventEntity evento;

    @ManyToMany(mappedBy = "ingressos")
    @JsonIgnore
    private List<BuyEntity> compras;
}
