package com.Eventicket.DTO.Consulta;

import com.Eventicket.Entities.Enum.StatusBuy;

import java.time.Instant;
import java.util.List;

public record BuyDTOConsulta(
        Long id,
        Instant data,
        Double total,
        StatusBuy statusBuy,
        Long usuarioId, // Apenas o ID do usuário
        String nomeCliente,
        List<TicketDTOConsulta> ingressos
) {
}