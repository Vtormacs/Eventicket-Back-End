package com.Eventicket.DTO.Mapper;

import com.Eventicket.DTO.Consulta.AddressDTOConsulta;
import com.Eventicket.DTO.Consulta.BuyDTOConsulta;
import com.Eventicket.DTO.Consulta.TicketDTOConsulta;
import com.Eventicket.DTO.Consulta.UserDTOConsulta;
import com.Eventicket.Entities.UserEntity;

import java.util.List;

public class UserMapper {

    public static UserDTOConsulta toUserDTO(UserEntity userEntity) {
        AddressDTOConsulta addressDTOConsulta = new AddressDTOConsulta(
                userEntity.getEndereco().getId(),
                userEntity.getEndereco().getEstado(),
                userEntity.getEndereco().getCidade(),
                userEntity.getEndereco().getRua(),
                userEntity.getEndereco().getNumero()
        );

        List<BuyDTOConsulta> comprasDTO = userEntity.getCompras().stream()
                .map(BuyMapper::toBuyDTO)
                .toList();

        List<TicketDTOConsulta> ingressosDTO = userEntity.getIngressos().stream()
                .map(TicketMapper::toTicketDTO)
                .toList();

        return new UserDTOConsulta(
                userEntity.getId(),
                userEntity.getAtivo(),
                userEntity.getRole(),
                userEntity.getNome(),
                userEntity.getCpf(),
                userEntity.getEmail(),
                userEntity.getCelular(),
                addressDTOConsulta,
                comprasDTO,
                ingressosDTO
        );
    }
}
