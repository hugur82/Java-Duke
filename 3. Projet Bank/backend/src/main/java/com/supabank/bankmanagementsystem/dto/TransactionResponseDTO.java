package com.supabank.bankmanagementsystem.dto;

import com.supabank.bankmanagementsystem.entity.TransactionStatus;
import com.supabank.bankmanagementsystem.entity.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class TransactionResponseDTO {

    private Long transactionId;
    private LocalDateTime transactionDate;
    private BigDecimal amount;
    private TransactionType transactionType;
    private String description;
    private TransactionStatus transactionStatus;
    private Long accountId;

    public TransactionResponseDTO(Long transactionId, LocalDateTime transactionDate, BigDecimal amount, TransactionType transactionType, String description, TransactionStatus transactionStatus, Long accountId) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.transactionType = transactionType;
        this.description = description;
        this.transactionStatus = transactionStatus;
        this.accountId = accountId;
    }
}
