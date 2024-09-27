package com.Eventicket.Entities;

import com.Eventicket.Entities.Enum.StatusTicket;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Enumerated(EnumType.STRING)
    private StatusTicket statusTicket;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"ingressos", "compras"})
    private UserEntity usuario;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    @JsonIgnoreProperties("ingressos")
    private EventEntity evento;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    @JsonIgnore
    private BuyEntity compra;

    public TicketEntity(StatusTicket statusTicket, UserEntity usuario, EventEntity evento, BuyEntity compra) {
        this.statusTicket = statusTicket;
        this.usuario = usuario;
        this.evento = evento;
        this.compra = compra;
    }
}
