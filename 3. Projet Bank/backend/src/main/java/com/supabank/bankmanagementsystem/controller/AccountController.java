package com.supabank.bankmanagementsystem.controller;

import com.supabank.bankmanagementsystem.dto.AccountCreateRequestDTO;
import com.supabank.bankmanagementsystem.dto.AccountResponseDTO;
import com.supabank.bankmanagementsystem.dto.AccountUpdateRequestDTO;
import com.supabank.bankmanagementsystem.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public List<AccountResponseDTO> getAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/search")
    public Page<AccountResponseDTO> searchAccounts(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String accountNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "accountNumber") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return accountService.searchAccounts(
                firstName,
                lastName,
                accountNumber,
                page,
                size,
                sortBy,
                direction
        );
    }

    @GetMapping("/accounts/{id}")
    public AccountResponseDTO getAccount(@PathVariable Long id) {
        return accountService.findAccountById(id);
    }

    @PostMapping("/accounts")
    public AccountResponseDTO createAccount(
            @RequestBody @Valid AccountCreateRequestDTO accountCreateRequestDTO
    ) {
        return accountService.createAccount(
                accountCreateRequestDTO
        );
    }

    @DeleteMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(
            @PathVariable("id") Long id
    ) {
        accountService.deleteAccountById(id);
    }

    @PatchMapping("/accounts/{id}")
    public AccountResponseDTO updateAccount(
            @Valid @RequestBody AccountUpdateRequestDTO accountUpdateRequestDTO,
            @PathVariable Long id
    ) {
        return accountService.updateAccount(
                id,
                accountUpdateRequestDTO
        );
    }
}