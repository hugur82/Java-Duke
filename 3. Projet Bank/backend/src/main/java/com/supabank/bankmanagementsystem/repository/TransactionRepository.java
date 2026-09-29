package com.supabank.bankmanagementsystem.repository;

import com.supabank.bankmanagementsystem.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM TransactionEntity t
        WHERE t.account.customer.customerId = :customerId
    """)
    boolean existsByCustomerId(@Param("customerId") Long customerId);

    boolean existsByAccountAccountId(Long accountId);

    @Query("""
        SELECT t
        FROM TransactionEntity t
        JOIN t.account a
        JOIN a.customer c
        WHERE
            (:firstName IS NULL OR
                LOWER(c.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')))
        AND
            (:lastName IS NULL OR
                LOWER(c.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')))
        AND
            (:accountId IS NULL OR
                a.accountId = :accountId)
        """)
    Page<TransactionEntity> searchTransactions(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("accountId") Long accountId,
            Pageable pageable
    );
}