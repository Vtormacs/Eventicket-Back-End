package com.Eventicket.DTO.Consulta;
import com.Eventicket.Entities.Enum.Role;

import java.util.List;

public record UserDTOConsulta(Long id,
                              Boolean ativo,
                              Role role,
                              String nome,
                              String cpf,
                              String email,
                              String celular,
                              AddressDTOConsulta endereco,
                              List<BuyDTOConsulta> compras,
                              List<TicketDTOConsulta> ingressos) {
}

