package com.Eventicket.Repositories;

import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Entities.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT e FROM event e WHERE e.endereco.cidade = :cidade")
    List<EventEntity> buscarEventosDaMesmaCidade(@Param("cidade") String cidade);

    @Modifying
    @Transactional
    @Query("UPDATE users u SET u.nome = :nome, u.cpf = :cpf, u.email = :email, u.senha = :senha, u.celular = :celular WHERE u.id = :id")
    void atualizarUsuario(@Param("id") Long id, @Param("nome") String nome, @Param("cpf") String cpf, @Param("email") String email, @Param("senha") String senha, @Param("celular") String celular);
}