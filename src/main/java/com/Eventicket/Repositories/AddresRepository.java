package com.Eventicket.Repositories;

import com.Eventicket.Entities.AddresEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddresRepository extends JpaRepository<AddresEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE addres a SET a.rua = :rua, a.numero = :numero, a.cidade = :cidade, a.estado = :estado WHERE a.id = :id")
    void atualizarEndereco(@Param("id") Long id, @Param("rua") String rua, @Param("numero") String numero, @Param("cidade") String cidade, @Param("estado") String estado);

}
