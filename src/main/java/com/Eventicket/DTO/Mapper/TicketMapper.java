package com.Eventicket.DTO.Mapper;

import com.Eventicket.DTO.TicketDTO;
import com.Eventicket.Entities.TicketEntity;

public class TicketMapper {

    public static TicketDTO toTicketDTO(TicketEntity ticketEntity) {
        return new TicketDTO(
                ticketEntity.getId(),
                ticketEntity.getStatusTicket(),
                ticketEntity.getUsuario().getId(),
                ticketEntity.getEvento().getNome(),
                ticketEntity.getCompra().getId()
        );
    }
}
