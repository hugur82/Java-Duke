package com.supabank.bankmanagementsystem.dto;

import com.supabank.bankmanagementsystem.entity.TransactionStatus;
import com.supabank.bankmanagementsystem.entity.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponseDTO {

    private Long transactionId;
    private LocalDateTime transactionDate;
    private BigDecimal amount;
    private TransactionType transactionType;
    private String description;
    private TransactionStatus transactionStatus;

    // Source account
    private Long accountId;
    private String accountNumber;
    private String accountType;
    private Long customerId;
    private String customerFirstName;
    private String customerLastName;

    // Destination account
    private Long destinationAccountId;
    private String destinationAccountNumber;
    private String destinationAccountType;
    private Long destinationCustomerId;
    private String destinationCustomerFirstName;
    private String destinationCustomerLastName;

    public TransactionResponseDTO(
            Long transactionId,
            LocalDateTime transactionDate,
            BigDecimal amount,
            TransactionType transactionType,
            String description,
            TransactionStatus transactionStatus,
            Long accountId,
            String accountNumber,
            String accountType,
            Long customerId,
            String customerFirstName,
            String customerLastName,
            Long destinationAccountId,
            String destinationAccountNumber,
            String destinationAccountType,
            Long destinationCustomerId,
            String destinationCustomerFirstName,
            String destinationCustomerLastName
    ) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.transactionType = transactionType;
        this.description = description;
        this.transactionStatus = transactionStatus;

        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.customerId = customerId;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;

        this.destinationAccountId = destinationAccountId;
        this.destinationAccountNumber = destinationAccountNumber;
        this.destinationAccountType = destinationAccountType;
        this.destinationCustomerId = destinationCustomerId;
        this.destinationCustomerFirstName = destinationCustomerFirstName;
        this.destinationCustomerLastName = destinationCustomerLastName;
    }
}
