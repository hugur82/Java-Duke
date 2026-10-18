package com.supabank.bankmanagementsystem.dto;

import java.math.BigDecimal;

import com.supabank.bankmanagementsystem.entity.TransactionType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TransactionCreateRequestDTO {

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private TransactionType transactionType;

    private String description;

    @NotNull
    private Long accountId;

    private Long destinationAccountId;

}
