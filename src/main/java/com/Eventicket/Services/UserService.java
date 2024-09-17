package com.Eventicket.Services;

import com.Eventicket.Entities.AddresEntity;
import com.Eventicket.Entities.EmailEntity;
import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Entities.UserEntity;
import com.Eventicket.Services.Exception.Email.EmailSendException;
import com.Eventicket.Services.Exception.User.*;
import com.Eventicket.Repositories.AddresRepository;
import com.Eventicket.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

            if (!userEntity.getEmail().isEmpty()) {
                EmailEntity email = emailService.criarEmail(userEntity);
                emailService.enviaEmail(email);
            }

            return userEntity;
        } catch (EmailSendException e) {
            System.out.println("Erro ao enviar o e-mail: " + e.getMessage());
            throw e;
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                System.out.println("CPF já existe no sistema: " + e.getMessage());
                throw new UserCPFException();
            }
            System.out.println("Erro de integridade de dados: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("Erro ao salvar o usuário: " + e.getMessage());
            throw new UserSaveException("Erro ao salvar o usuário", e);
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
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UserUpdateException("Erro ao atualizar a usuario: " + e.getMessage());
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

    public List<UserEntity> findAll() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new UserFindAllException("Erro ao retornar a lista de usuarios" + e.getMessage());
        }
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
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
