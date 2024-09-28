package com.Eventicket.DTO;
import com.Eventicket.Entities.Enum.Role;

import java.util.List;

public record UserDTO(Long id,
                      Boolean ativo,
                      Role role,
                      String nome,
                      String cpf,
                      String email,
                      String celular,
                      AddressDTO endereco,
                      List<BuyDTO> compras,
                      List<TicketDTO> ingressos) {
}

