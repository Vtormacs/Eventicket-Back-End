package com.Eventicket.Services;

import com.Eventicket.Entities.AddresEntity;
import com.Eventicket.Entities.UserEntity;
import com.Eventicket.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity save(UserEntity userEntity) {
        try {
            return userRepository.save(userEntity);
        } catch (Exception e) {
            System.out.println("Erro ao salvar o usuário: " + e.getMessage());
            return new UserEntity();
        }
    }

    public UserEntity update(UserEntity userEntity, Long id) {
        try {
            Optional<UserEntity> userExistenteOp = userRepository.findById(id);
            if (userExistenteOp.isPresent()) {
                UserEntity userExistente = userExistenteOp.get();

                // Verifica se o endereço atual do usuário precisa ser atualizado
                if (userEntity.getEndereco() != null) {
                    AddresEntity addresAtualizado = userEntity.getEndereco();
                    AddresEntity addresExistente = userExistente.getEndereco();

                    // Verifica se o endereço já existe ou precisa ser atualizado
                    if (addresExistente != null) {
                        addresExistente.setEstado(addresAtualizado.getEstado());
                        addresExistente.setCidade(addresAtualizado.getCidade());
                        addresExistente.setRua(addresAtualizado.getRua());
                        addresExistente.setNumero(addresAtualizado.getNumero());
                    } else {
                        // Se não houver endereço existente, associa o novo endereço
                        userExistente.setEndereco(addresAtualizado);
                    }
                }

                // Atualiza outros campos do usuário
                userExistente.setNome(userEntity.getNome());
                userExistente.setCpf(userEntity.getCpf());
                userExistente.setEmail(userEntity.getEmail());
                userExistente.setSenha(userEntity.getSenha());
                userExistente.setCelular(userEntity.getCelular());

                // Salva o usuário com o endereço atualizado (ou o mesmo endereço se não mudou)
                return userRepository.save(userExistente);
            } else {
                System.out.println("Usuário não encontrado com o ID: " + id);
                return new UserEntity();
            }
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
        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> {
                        System.out.println("Usuário não encontrado com o ID: " + id);
                        return new RuntimeException("Usuário não encontrado");
                    });
        } catch (Exception e) {
            System.out.println("Erro ao buscar o usuário: " + e.getMessage());
            return new UserEntity();
        }
    }
}
