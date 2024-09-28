package com.Eventicket.DTO;

import com.Eventicket.Entities.Enum.StatusBuy;

import java.time.Instant;
import java.util.List;

public record BuyDTO(
        Long id,
        Instant data,
        Double total,
        StatusBuy statusBuy,
        Long usuarioId, // Apenas o ID do usuário
        List<TicketDTO> ingressos
) {
}