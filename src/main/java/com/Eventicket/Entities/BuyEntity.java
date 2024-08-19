package com.Eventicket.Entities;

import com.Eventicket.Entities.Enums.StatusBuy;
import com.Eventicket.Entities.Enums.StatusTicket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "buy")
@Table(name = "buy")
public class BuyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant data;

    ///EnumType.STRING: Isso especifica que o valor da enumeração será armazenado como uma string no banco de dados, em vez de um número inteiro.
    //@Enumerated: Essa anotação é usada para mapear o campo da enumeração statusBuy para uma coluna no banco de dados.

    @Enumerated(EnumType.STRING)
    private StatusBuy statusBuy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("compras")
    private UserEntity usuario;

    @ManyToMany
    @JoinTable(
            name = "buy_ticket",
            joinColumns = @JoinColumn(name = "buy_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id")
    )
    @JsonIgnoreProperties("compras")
    private List<TicketEntity> ingressos;

    @NotNull
    Double total;
}
