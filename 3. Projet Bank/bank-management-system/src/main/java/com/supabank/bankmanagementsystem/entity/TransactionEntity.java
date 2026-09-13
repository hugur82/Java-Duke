package com.supabank.bankmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="bank_transaction")
@Getter @Setter
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transaction_id")
    private Long transactionId;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @NotNull
    @Positive
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus transactionStatus;

    @ManyToOne
    @JoinColumn(name="account_id")
    @NotNull
    private AccountEntity account;

}

