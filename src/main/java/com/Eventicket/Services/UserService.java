package com.Eventicket.Services;

import com.Eventicket.Entities.AddresEntity;
import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Entities.UserEntity;
import com.Eventicket.Services.Exception.User.UserNotFoundException;
import com.Eventicket.Repositories.AddresRepository;
import com.Eventicket.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddresRepository addresRepository;

    @Autowired
    private EmailService emailService;

    public UserEntity save(UserEntity userEntity) {
        try {

            userRepository.save(userEntity);

            if (!userEntity.getEmail().isEmpty()){
                emailService.enviaEmail(userEntity);
            }

            return userEntity;
        } catch (Exception e) {
            System.out.println("Erro ao salvar o usuário: " + e.getMessage());
            return new UserEntity();
        }
    }

    public UserEntity update(UserEntity userEntity, Long id) {
        try {
            UserEntity usuarioExistente = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

            userRepository.atualizarUsuario(id, userEntity.getNome(), userEntity.getCpf(), userEntity.getEmail(), userEntity.getSenha(), userEntity.getCelular());

            AddresEntity novoEndereco = userEntity.getEndereco();
            if (novoEndereco != null && usuarioExistente.getEndereco() != null) {
                addresRepository.atualizarEndereco(usuarioExistente.getEndereco().getId(), novoEndereco.getRua(), novoEndereco.getNumero(), novoEndereco.getCidade(), novoEndereco.getEstado());
            }

            return usuarioExistente;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o usuário: " + e.getMessage());
            return new UserEntity();
        }
    }

    public String delete(Long id) {
        try {
            if (userRepository.findById(id).isPresent()) {
                userRepository.deleteById(id);
                return "Usuário deletado com sucesso!";
            } else {
                return "Usuário não encontrado";
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar o usuário: " + e.getMessage());
            return "Erro ao deletar o usuário";
        }
    }

    public List<UserEntity> findAll() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao retornar a lista de usuários: " + e.getMessage());
            return List.of();
        }
    }

    public UserEntity findById(Long id) {
            return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    public List<EventEntity> buscarEventosDaMesmaCidade (Long idUsuario){
        try {
            UserEntity usuario = userRepository.findById(idUsuario).orElseThrow(() -> new UserNotFoundException());

            String cidade = usuario.getEndereco().getCidade();

            return userRepository.buscarEventosDaMesmaCidade(cidade);
        } catch (Exception e) {
            System.out.println("Erro ao retornar a lista de usuários: " + e.getMessage());
            return List.of();
        }
    }
}
