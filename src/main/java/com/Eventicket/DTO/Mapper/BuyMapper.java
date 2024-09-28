package com.Eventicket.DTO.Mapper;

import com.Eventicket.DTO.BuyDTO;
import com.Eventicket.DTO.TicketDTO;
import com.Eventicket.Entities.BuyEntity;

import java.util.List;

public class BuyMapper {

    public static BuyDTO toBuyDTO(BuyEntity buyEntity) {
        List<TicketDTO> ingressosDTO = buyEntity.getIngressos().stream()
                .map(TicketMapper::toTicketDTO)
                .toList();

        return new BuyDTO(
                buyEntity.getId(),
                buyEntity.getData(),
                buyEntity.getTotal(),
                buyEntity.getStatusBuy(),
                buyEntity.getUsuario().getId(),
                ingressosDTO
        );
    }
}
