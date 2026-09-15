package com.supabank.bankmanagementsystem.dto;

import com.supabank.bankmanagementsystem.entity.AccountStatus;
import com.supabank.bankmanagementsystem.entity.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccountResponseDTO {

    private Long accountId;
    private String iban;
    private String accountNumber;
    private BigDecimal balance;
    private LocalDateTime creationDate;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private Long customerId;

    public AccountResponseDTO(
            Long accountId,
            String iban,
            String accountNumber,
            BigDecimal balance,
            LocalDateTime creationDate,
            AccountType accountType,
            AccountStatus accountStatus,
            Long customerId
    ) {
        this.accountId = accountId;
        this.iban = iban;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.creationDate = creationDate;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.customerId = customerId;
    }

}
