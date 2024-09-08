package com.Eventicket.Repositories;

import com.Eventicket.Entities.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {
}
