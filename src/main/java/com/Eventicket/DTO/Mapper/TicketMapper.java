package com.Eventicket.DTO.Mapper;

import com.Eventicket.DTO.Consulta.TicketDTOConsulta;
import com.Eventicket.Entities.TicketEntity;

public class TicketMapper {

    public static TicketDTOConsulta toTicketDTO(TicketEntity ticketEntity) {
        return new TicketDTOConsulta(
                ticketEntity.getId(),
                ticketEntity.getStatusTicket(),
                ticketEntity.getUsuario().getId(),
                ticketEntity.getEvento().getNome(),
                ticketEntity.getCompra().getId()
        );
    }
}
