package com.Eventicket.Repositories;

import com.Eventicket.Entities.BuyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyRepository extends JpaRepository<BuyEntity, Long> {
}