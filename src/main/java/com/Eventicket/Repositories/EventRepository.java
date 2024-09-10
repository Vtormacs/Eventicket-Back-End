package com.Eventicket.Repositories;

import com.Eventicket.Entities.EventEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE event e SET e.nome = :nome, e.data = :data, e.descricao = :descricao, e.quantidade = :quantidade WHERE e.id = :id")
    void atualizarEvento(@Param("id") Long id, @Param("nome") String nome, @Param("data") Date data, @Param("descricao") String descricao, @Param("quantidade") Integer quantidade);

    List<EventEntity> findByEndereco_Cidade(String cidade);

    //@Modifying
    //@Transactional
    //@Query("UPDATE ticket t SET t.preco = :preco, t.statusTicket = :statusTicket, t.categoryTicket = :categoryTicket WHERE t.id = :id")
    //void atualizarIngresso(@Param("id") Long id, @Param("preco") Double preco,  @Param("statusTicket") StatusTicket statusTicket, @Param("categoryTicket") CategoryTicket categoryTicket);
}