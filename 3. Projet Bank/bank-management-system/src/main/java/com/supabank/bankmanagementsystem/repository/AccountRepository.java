package com.supabank.bankmanagementsystem.repository;

import com.supabank.bankmanagementsystem.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity,Long> {
    boolean existsByAccountNumber(String accountNumber);
}
