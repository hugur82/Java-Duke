package com.supabank.bankmanagementsystem.dto;

import com.supabank.bankmanagementsystem.entity.AccountStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountUpdateRequestDTO {

    @NotNull
    private AccountStatus status;
}
