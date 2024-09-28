package com.Eventicket.DTO;

import com.Eventicket.Entities.Enum.StatusTicket;

public record TicketDTO(
        Long id,
        StatusTicket statusTicket,
        Long usuarioId, // Apenas o ID do usuário
        String eventoNome, // Apenas o nome do evento
        Long compraId // Apenas o ID da compra
) {
}
