package com.supabank.bankmanagementsystem.controller;

import com.supabank.bankmanagementsystem.dto.AccountRequestDTO;
import com.supabank.bankmanagementsystem.dto.AccountResponseDTO;
import com.supabank.bankmanagementsystem.service.AccountService;
import jakarta.validation.Valid;
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

    @GetMapping("/accounts/{id}")
    public AccountResponseDTO getAccount(@PathVariable Long id){
        return accountService.findAccountById(id);
    }

    @PostMapping("/accounts")
    public AccountResponseDTO createAccount(@RequestBody @Valid AccountRequestDTO accountRequestDTO) {
        return accountService.createAccount(accountRequestDTO);
    }

    @DeleteMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable("id")  Long id) {
        accountService.deleteAccountById(id);
    }

}
