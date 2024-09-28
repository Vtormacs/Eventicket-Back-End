package com.Eventicket.DTO;

import java.time.LocalDate;
import java.util.Set;

public record EventDTO(
        Long id,
        String nome,
        Double precoDoIngresso,
        Integer quantidade,
        LocalDate data,
        String descricao,
        AddressDTO endereco,
        Set<CategoryDTO> categories
) {
}
