package com.Eventicket.DTO.Mapper;

import com.Eventicket.DTO.AddressDTO;
import com.Eventicket.DTO.BuyDTO;
import com.Eventicket.DTO.TicketDTO;
import com.Eventicket.DTO.UserDTO;
import com.Eventicket.Entities.UserEntity;

import java.util.List;

public class UserMapper {

    public static UserDTO toUserDTO(UserEntity userEntity) {
        AddressDTO addressDTO = new AddressDTO(
                userEntity.getEndereco().getId(),
                userEntity.getEndereco().getEstado(),
                userEntity.getEndereco().getCidade(),
                userEntity.getEndereco().getRua(),
                userEntity.getEndereco().getNumero()
        );

        List<BuyDTO> comprasDTO = userEntity.getCompras().stream()
                .map(BuyMapper::toBuyDTO)
                .toList();

        List<TicketDTO> ingressosDTO = userEntity.getIngressos().stream()
                .map(TicketMapper::toTicketDTO)
                .toList();

        return new UserDTO(
                userEntity.getId(),
                userEntity.getAtivo(),
                userEntity.getRole(),
                userEntity.getNome(),
                userEntity.getCpf(),
                userEntity.getEmail(),
                userEntity.getCelular(),
                addressDTO,
                comprasDTO,
                ingressosDTO
        );
    }
}
