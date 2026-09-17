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
    private String accountNumber;

    private Long customerId;
    private String customerFirstName;
    private String customerLastName;

    public TransactionResponseDTO(
            Long transactionId,
            LocalDateTime transactionDate,
            BigDecimal amount,
            TransactionType transactionType,
            String description,
            TransactionStatus transactionStatus,
            Long accountId,
            String accountNumber,
            Long customerId,
            String customerFirstName,
            String customerLastName
    ) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.transactionType = transactionType;
        this.description = description;
        this.transactionStatus = transactionStatus;
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
    }
}
