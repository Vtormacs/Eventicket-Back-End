package com.Eventicket.Services;

import com.Eventicket.DTO.Mapper.UserMapper;
import com.Eventicket.DTO.Consulta.UserDTOConsulta;
import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Entities.UserEntity;
import com.Eventicket.Exception.User.*;
import com.Eventicket.Repositories.AddresRepository;
import com.Eventicket.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity update2(UserEntity userEntity, Long id) {
        try {
            UserEntity userExistente = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

            userEntity.setId(id);

            if (userEntity.getEndereco() != null && userExistente.getEndereco() != null) {
                userEntity.getEndereco().setId(userExistente.getEndereco().getId());
            }

            userEntity.setCompras(userExistente.getCompras());
            userEntity.setIngressos(userExistente.getIngressos());

            return userRepository.save(userEntity);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UserUpdateException("Erro ao atualizar o usuário: " + e.getMessage());
        }
    }

    public String delete(Long id) {
        try {
            userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
            userRepository.deleteById(id);
            return "Usuario Deletado";
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UserDeleteException("Erro ao deletar o usuário: " + e.getMessage());
        }
    }

    public List<UserDTOConsulta> findAll() {
        try {
            List<UserEntity> lista = userRepository.findAll();

            List<UserDTOConsulta> dtos = lista.stream().map(UserMapper::toUserDTO).collect(Collectors.toList());

            return dtos;
        } catch (Exception e) {
            throw new UserFindAllException("Erro ao retornar a lista de usuarios" + e.getMessage());
        }
    }

    public UserDTOConsulta findById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

        return UserMapper.toUserDTO(userEntity);
    }

    public List<EventEntity> buscarEventosDaMesmaCidade(Long idUsuario) {
        try {
            UserEntity usuario = userRepository.findById(idUsuario).orElseThrow(() -> new UserNotFoundException());

            String cidade = usuario.getEndereco().getCidade();

            return userRepository.buscarEventosDaMesmaCidade(cidade);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new buscarEventosDaMesmaCidadeException("Erro ao retornar a lista de usuários: " + e.getMessage());
        }
    }

    public boolean validarConta(Long idUser, String hashRecebido) {
        UserEntity usuario = userRepository.findById(idUser).orElseThrow(() -> new UserNotFoundException());

        String hashGerado = EmailService.generateHash(usuario.getNome(), usuario.getEmail());

        if (hashGerado.equals(hashRecebido)) {
            usuario.setAtivo(true);
            userRepository.save(usuario);
            return true;
        }
        return false;
    }
}
