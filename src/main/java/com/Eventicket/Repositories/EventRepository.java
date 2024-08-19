package com.Eventicket.Repositories;

import com.Eventicket.Entities.Enums.CategoryTicket;
import com.Eventicket.Entities.Enums.StatusTicket;
import com.Eventicket.Entities.EventEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE event e SET e.nome = :nome, e.data = :data, e.descricao = :descricao WHERE e.id = :id")
    void atualizarEvento(@Param("id") Long id, @Param("nome") String nome, @Param("data") Date data, @Param("descricao") String descricao);

    @Modifying
    @Transactional
    @Query("UPDATE addres a SET a.rua = :rua, a.numero = :numero, a.cidade = :cidade, a.estado = :estado WHERE a.id = :id")
    void atualizarEndereco(@Param("id") Long id, @Param("rua") String rua, @Param("numero") String numero, @Param("cidade") String cidade, @Param("estado") String estado);

    @Modifying
    @Transactional
    @Query("UPDATE ticket t SET t.preco = :preco, t.quantidade = :quantidade, t.statusTicket = :statusTicket, t.categoryTicket = :categoryTicket WHERE t.id = :id")
    void atualizarIngresso(@Param("id") Long id, @Param("preco") Double preco, @Param("quantidade") Integer quantidade, @Param("statusTicket") StatusTicket statusTicket, @Param("categoryTicket") CategoryTicket categoryTicket);
}
