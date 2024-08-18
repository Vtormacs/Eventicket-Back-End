package com.Eventicket.Entities;

import com.Eventicket.Entities.Enums.StatusTicket;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Event;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ticket")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double preco;

    @NotNull
    private Integer quantidade;

    ///EnumType.STRING: Isso especifica que o valor da enumeração será armazenado como uma string no banco de dados, em vez de um número inteiro.
    //@Enumerated: Essa anotação é usada para mapear o campo da enumeração statusTicket para uma coluna no banco de dados.

    @Enumerated(EnumType.STRING)
    private StatusTicket statusTicket;

    @ManyToOne()
    @JoinColumn(name = "evento_id")
    private EventEntity evento;
}
