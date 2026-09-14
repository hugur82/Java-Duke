package com.supabank.bankmanagementsystem.service;

import com.supabank.bankmanagementsystem.dto.TransactionCreateRequestDTO;
import com.supabank.bankmanagementsystem.dto.TransactionResponseDTO;
import com.supabank.bankmanagementsystem.dto.TransactionUpdateRequestDTO;
import com.supabank.bankmanagementsystem.entity.*;
import com.supabank.bankmanagementsystem.exception.AccountNotFoundException;
import com.supabank.bankmanagementsystem.exception.TransactionNotFoundException;
import com.supabank.bankmanagementsystem.exception.TransactionProcessingNotAllowedException;
import com.supabank.bankmanagementsystem.exception.TransactionUpdateNotAllowedException;
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


    public TransactionResponseDTO updateTransaction(
            Long transactionId,
            TransactionUpdateRequestDTO transactionUpdateRequestDTO) {

        TransactionEntity transactionEntity = transactionRepository
                .findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(
                        "Transaction not found with id " + transactionId));

        if (transactionEntity.getTransactionStatus() != TransactionStatus.CREATED) {
            throw new TransactionUpdateNotAllowedException(
                    "Transaction can only be updated when its status is CREATED");
        }

        transactionEntity.setAmount(transactionUpdateRequestDTO.getAmount());
        transactionEntity.setDescription(transactionUpdateRequestDTO.getDescription());

        return toResponseDTO(transactionRepository.save(transactionEntity));
    }

    public TransactionResponseDTO processTransaction(Long transactionId) {

        TransactionEntity transactionEntity = transactionRepository
                .findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(
                        "Transaction not found with id " + transactionId));

        if (transactionEntity.getTransactionStatus() != TransactionStatus.CREATED) {
            throw new TransactionProcessingNotAllowedException(
                    "Transaction can only be processed when its status is CREATED");
        }

        AccountEntity accountEntity = transactionEntity.getAccount();

        if (accountEntity.getAccountStatus() != AccountStatus.ACTIVE) {
            transactionEntity.setTransactionStatus(TransactionStatus.REJECTED);
            return toResponseDTO(transactionRepository.save(transactionEntity));
        }

        transactionEntity.setTransactionStatus(TransactionStatus.PROCESSING);

        if (transactionEntity.getTransactionType() == TransactionType.DEPOSIT) {

            accountEntity.setBalance(
                    accountEntity.getBalance().add(transactionEntity.getAmount())
            );

            transactionEntity.setTransactionStatus(TransactionStatus.ACCEPTED);

        } else if (transactionEntity.getTransactionType() == TransactionType.WITHDRAWAL) {

            if (accountEntity.getBalance().compareTo(transactionEntity.getAmount()) >= 0) {

                accountEntity.setBalance(
                        accountEntity.getBalance().subtract(transactionEntity.getAmount())
                );

                transactionEntity.setTransactionStatus(TransactionStatus.ACCEPTED);

            } else {
                transactionEntity.setTransactionStatus(TransactionStatus.REJECTED);
            }
        }

        accountRepository.save(accountEntity);

        return toResponseDTO(transactionRepository.save(transactionEntity));
    }
}
