package com.Eventicket.DTO.Consulta;

import java.time.LocalDate;
import java.util.Set;

public record EventDTOConsulta(
        Long id,
        String nome,
        Double precoDoIngresso,
        Integer quantidade,
        LocalDate data,
        String descricao,
        AddressDTOConsulta endereco,
        Set<CategoryDTOConsulta> categories
) {
}
