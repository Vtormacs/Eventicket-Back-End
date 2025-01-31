package com.Eventicket.DTO.Mapper;

import com.Eventicket.DTO.Consulta.AddressDTOConsulta;
import com.Eventicket.DTO.Consulta.CategoryDTOConsulta;
import com.Eventicket.DTO.Consulta.EventDTOConsulta;
import com.Eventicket.Entities.EventEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class EventMapper {

    public static EventDTOConsulta toEventDTO(EventEntity eventEntity) {
        // Mapeia o endereço
        AddressDTOConsulta addressDTOConsulta = new AddressDTOConsulta(
                eventEntity.getEndereco().getId(),
                eventEntity.getEndereco().getEstado(),
                eventEntity.getEndereco().getCidade(),
                eventEntity.getEndereco().getRua(),
                eventEntity.getEndereco().getNumero()
        );

        // Mapeia as categorias associadas
        Set<CategoryDTOConsulta> categoriesDTO = eventEntity.getCategories().stream()
                .map(category -> new CategoryDTOConsulta(
                        category.getId(),
                        category.getNome()
                ))
                .collect(Collectors.toSet());

        // Retorna o EventDTOConsulta
        return new EventDTOConsulta(
                eventEntity.getId(),
                eventEntity.getNome(),
                eventEntity.getPrecoDoIngresso(),
                eventEntity.getQuantidade(),
                eventEntity.getData(),
                eventEntity.getDescricao(),
                addressDTOConsulta,
                categoriesDTO
        );
    }
}
