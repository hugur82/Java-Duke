package com.supabank.bankmanagementsystem.repository;

import com.supabank.bankmanagementsystem.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
