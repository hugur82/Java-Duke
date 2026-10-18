
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
        WHERE
            t.account.customer.customerId = :customerId
            OR t.destinationAccount.customer.customerId = :customerId
    """)
    boolean existsByCustomerId(@Param("customerId") Long customerId);

    boolean existsByAccountAccountId(Long accountId);

    @Query("""
        SELECT t
        FROM TransactionEntity t
        LEFT JOIN t.account sourceAccount
        LEFT JOIN sourceAccount.customer sourceCustomer
        LEFT JOIN t.destinationAccount destinationAccount
        LEFT JOIN destinationAccount.customer destinationCustomer
        WHERE
            (:firstName IS NULL OR
                LOWER(sourceCustomer.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))
                OR LOWER(destinationCustomer.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')))
        AND
            (:lastName IS NULL OR
                LOWER(sourceCustomer.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))
                OR LOWER(destinationCustomer.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')))
        AND
            (:accountId IS NULL OR
                sourceAccount.accountId = :accountId
                OR destinationAccount.accountId = :accountId)
        """)
    Page<TransactionEntity> searchTransactions(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("accountId") Long accountId,
            Pageable pageable
    );
}
