package com.supabank.bankmanagementsystem.controller;

import com.supabank.bankmanagementsystem.dto.TransactionCreateRequestDTO;
import com.supabank.bankmanagementsystem.dto.TransactionResponseDTO;
import com.supabank.bankmanagementsystem.dto.TransactionUpdateRequestDTO;
import com.supabank.bankmanagementsystem.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public List<TransactionResponseDTO> getTransactions(){
        return transactionService.findAll();
    }

    @GetMapping("/transactions/{id}")
    public TransactionResponseDTO getTransactionById(@PathVariable(name = "id") long transactionId){
        return transactionService.findTransactionById(transactionId);
    }

    @PostMapping("/transactions")
    public TransactionResponseDTO createTransaction(@RequestBody @Valid TransactionCreateRequestDTO transactionCreateRequestDTO){
        return transactionService.createTransaction(transactionCreateRequestDTO);
    }

    @PatchMapping("/transactions/{id}")
    public TransactionResponseDTO updateTransaction(@PathVariable(name = "id") long transactionId, @RequestBody @Valid TransactionUpdateRequestDTO transactionUpdateRequestDTO){
        return transactionService.updateTransaction(transactionId, transactionUpdateRequestDTO);
    }

    @PatchMapping("/transactions/{id}/process")
    public TransactionResponseDTO processTransaction(
            @PathVariable(name = "id") Long transactionId) {
        return transactionService.processTransaction(transactionId);
    }
}
