package com.Eventicket.Entities;
import com.Eventicket.Entities.Enums.StatusTicket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private StatusTicket statusTicket;

    @ManyToOne
    @JsonIgnoreProperties("ingressos")
    private UserEntity usuario;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private EventEntity evento;

    public TicketEntity(StatusTicket statusTicket, UserEntity usuario, EventEntity evento) {
        this.statusTicket = statusTicket;
        this.usuario = usuario;
        this.evento = evento;
    }
}