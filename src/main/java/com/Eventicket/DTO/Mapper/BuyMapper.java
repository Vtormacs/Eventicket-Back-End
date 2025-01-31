package com.Eventicket.DTO.Mapper;

import com.Eventicket.DTO.Consulta.BuyDTOConsulta;
import com.Eventicket.DTO.Consulta.TicketDTOConsulta;
import com.Eventicket.Entities.BuyEntity;

import java.util.List;

public class BuyMapper {

    public static BuyDTOConsulta toBuyDTO(BuyEntity buyEntity) {
        List<TicketDTOConsulta> ingressosDTO = buyEntity.getIngressos().stream()
                .map(TicketMapper::toTicketDTO)
                .toList();

        return new BuyDTOConsulta(
                buyEntity.getId(),
                buyEntity.getData(),
                buyEntity.getTotal(),
                buyEntity.getStatusBuy(),
                buyEntity.getUsuario().getId(),
                buyEntity.getUsuario().getNome(),
                ingressosDTO
        );
    }
}
