package com.supabank.bankmanagementsystem.repository;

import com.supabank.bankmanagementsystem.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {
}
