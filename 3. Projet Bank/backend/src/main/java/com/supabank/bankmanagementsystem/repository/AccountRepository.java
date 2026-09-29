package com.supabank.bankmanagementsystem.repository;

import com.supabank.bankmanagementsystem.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    boolean existsByAccountNumber(String accountNumber);

    void deleteByCustomerCustomerId(Long customerId);

    @Query("""
            SELECT a
            FROM AccountEntity a
            JOIN a.customer c
            WHERE
                (:firstName IS NULL OR
                    LOWER(c.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')))
            AND
                (:lastName IS NULL OR
                    LOWER(c.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')))
            AND
                (:accountNumber IS NULL OR
                    a.accountNumber LIKE CONCAT('%', :accountNumber, '%'))
            """)
    Page<AccountEntity> searchAccounts(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("accountNumber") String accountNumber,
            Pageable pageable
    );
}