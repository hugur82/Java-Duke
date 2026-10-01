package com.supabank.bankmanagementsystem.repository;

import com.supabank.bankmanagementsystem.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
