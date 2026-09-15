package com.supabank.bankmanagementsystem.dto;

import com.supabank.bankmanagementsystem.entity.AccountType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreateRequestDTO {
    @NotNull
    private Long customerId;
    @NotNull
    private AccountType accountType;
}
