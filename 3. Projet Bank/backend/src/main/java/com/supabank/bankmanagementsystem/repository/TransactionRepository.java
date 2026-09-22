package com.supabank.bankmanagementsystem.repository;

import com.supabank.bankmanagementsystem.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {
    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM TransactionEntity t
        WHERE t.account.customer.customerId = :customerId
    """)
    boolean existsByCustomerId(@Param("customerId") Long customerId);

    boolean existsByAccountAccountId(Long accountId);
}
