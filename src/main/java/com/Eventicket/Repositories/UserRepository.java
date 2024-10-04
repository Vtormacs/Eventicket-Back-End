package com.Eventicket.Repositories;

import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT e FROM event e WHERE e.endereco.cidade = :cidade")
    List<EventEntity> buscarEventosDaMesmaCidade(@Param("cidade") String cidade);

    UserDetails findByEmail(String email);

    List<UserEntity> findByNome(String nome);
}