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

    @Enumerated(EnumType.STRING)
    private StatusTicket statusTicket;

    @ManyToOne
    @JsonIgnoreProperties("ingressos")
    private UserEntity usuario;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private EventEntity evento;
}