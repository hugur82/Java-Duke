package com.supabank.bankmanagementsystem.service;

import com.supabank.bankmanagementsystem.dto.TransactionCreateRequestDTO;
import com.supabank.bankmanagementsystem.dto.TransactionResponseDTO;
import com.supabank.bankmanagementsystem.entity.AccountEntity;
import com.supabank.bankmanagementsystem.entity.TransactionEntity;
import com.supabank.bankmanagementsystem.entity.TransactionStatus;
import com.supabank.bankmanagementsystem.exception.AccountNotFoundException;
import com.supabank.bankmanagementsystem.exception.TransactionNotFoundException;
import com.supabank.bankmanagementsystem.repository.AccountRepository;
import com.supabank.bankmanagementsystem.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public List<TransactionResponseDTO> findAll() {
        return transactionRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public TransactionResponseDTO toResponseDTO(TransactionEntity transactionEntity) {
        return new TransactionResponseDTO(transactionEntity.getTransactionId(),
                transactionEntity.getTransactionDate(),
                transactionEntity.getAmount(),
                transactionEntity.getTransactionType(),
                transactionEntity.getDescription(),
                transactionEntity.getTransactionStatus(),
                transactionEntity.getAccount().getAccountId());
    }

    public TransactionResponseDTO createTransaction(TransactionCreateRequestDTO transactionCreateRequestDTO) {
        TransactionEntity transactionEntity = new TransactionEntity();
        AccountEntity accountEntity = accountRepository
                .findById(transactionCreateRequestDTO.getAccountId())
                .orElseThrow(
                        () -> new AccountNotFoundException(
                                "Account not found with id " + transactionCreateRequestDTO.getAccountId()));

        transactionEntity.setTransactionDate(LocalDateTime.now());
        transactionEntity.setAmount(transactionCreateRequestDTO.getAmount());
        transactionEntity.setTransactionType(transactionCreateRequestDTO.getTransactionType());
        transactionEntity.setDescription(transactionCreateRequestDTO.getDescription());
        transactionEntity.setTransactionStatus(TransactionStatus.CREATED);
        transactionEntity.setAccount(accountEntity);

        return toResponseDTO(transactionRepository.save(transactionEntity));
    }

    public TransactionResponseDTO findTransactionById(Long transactionId) {
        TransactionEntity transactionEntity = transactionRepository
                .findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(
                        "Transaction not found with id " + transactionId));
        return toResponseDTO(transactionEntity);
    }
}
