package com.Eventicket.Services;

import com.Eventicket.Entities.UserEntity;
import com.Eventicket.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity save(UserEntity userEntity){
        try{
            return userRepository.save(userEntity);
        }catch (Exception e){
            System.out.println("Erro ao salvar o usuário. " + e.getMessage());
            return new UserEntity();
        }
    }

    public UserEntity findById(Long id){
        try {
            return userRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            System.out.println("Erro ao procurar o usuário com ID " + id + ": " + e.getMessage());
            return new UserEntity();
        }
    }

    public List<UserEntity> findAll(){
        try {
            return userRepository.findAll();
        }catch (Exception e){
            System.out.println("Erro ao procurar todos os usuarios. " + e.getMessage());
            return List.of();
        }
    }

    public UserEntity update(UserEntity userEntity, Long id){
        try{
            userEntity.setId(id);
            return userRepository.save(userEntity);
        }catch (Exception e){
            System.out.println("Erro ao atualizar usuário com ID " + id + ": " + e.getMessage());
            return new UserEntity();
        }
    }

    public String delete(Long id){
        try {
            userRepository.deleteById(id);
            return "Usuário deletado com sucesso!";
        }catch (Exception e){
            System.out.println("Erro ao deletar usuário : " + e);
            return "Erro ao deletar usuário.";
        }
    }

}
