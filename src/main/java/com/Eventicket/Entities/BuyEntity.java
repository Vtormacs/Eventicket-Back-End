package com.Eventicket.Entities;

import com.Eventicket.Entities.Enums.StatusBuy;
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

    @NotNull
    private Double total;

    @Enumerated(EnumType.STRING)
    private StatusBuy statusBuy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"compras", "ingressos"})
    private UserEntity usuario;

    @OneToMany(mappedBy = "compra")
    @JsonIgnoreProperties({"compra", "usuario"})
    private List<TicketEntity> ingressos;

    public BuyEntity(Instant data, Double total, StatusBuy statusBuy, UserEntity usuario) {
        this.data = data;
        this.total = total;
        this.statusBuy = statusBuy;
        this.usuario = usuario;
    }
}
