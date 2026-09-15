package com.supabank.bankmanagementsystem.dto;

import com.supabank.bankmanagementsystem.entity.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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

}
