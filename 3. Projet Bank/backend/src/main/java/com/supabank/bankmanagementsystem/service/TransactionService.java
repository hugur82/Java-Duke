package com.supabank.bankmanagementsystem.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.supabank.bankmanagementsystem.dto.TransactionCreateRequestDTO;
import com.supabank.bankmanagementsystem.dto.TransactionResponseDTO;
import com.supabank.bankmanagementsystem.dto.TransactionUpdateRequestDTO;
import com.supabank.bankmanagementsystem.entity.AccountEntity;
import com.supabank.bankmanagementsystem.entity.AccountStatus;
import com.supabank.bankmanagementsystem.entity.TransactionEntity;
import com.supabank.bankmanagementsystem.entity.TransactionStatus;
import com.supabank.bankmanagementsystem.entity.TransactionType;
import com.supabank.bankmanagementsystem.exception.AccountNotFoundException;
import com.supabank.bankmanagementsystem.exception.TransactionNotFoundException;
import com.supabank.bankmanagementsystem.exception.TransactionProcessingNotAllowedException;
import com.supabank.bankmanagementsystem.exception.TransactionUpdateNotAllowedException;
import com.supabank.bankmanagementsystem.repository.AccountRepository;
import com.supabank.bankmanagementsystem.repository.TransactionRepository;
import org.springframework.transaction.annotation.Transactional;

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
        return new TransactionResponseDTO(
                transactionEntity.getTransactionId(),
                transactionEntity.getTransactionDate(),
                transactionEntity.getAmount(),
                transactionEntity.getTransactionType(),
                transactionEntity.getDescription(),
                transactionEntity.getTransactionStatus(),

                transactionEntity.getAccount().getAccountId(),
                transactionEntity.getAccount().getAccountNumber(),

                transactionEntity.getAccount().getCustomer().getCustomerId(),
                transactionEntity.getAccount().getCustomer().getFirstName(),
                transactionEntity.getAccount().getCustomer().getLastName()
        );
    }


public TransactionResponseDTO createTransaction(
        TransactionCreateRequestDTO transactionCreateRequestDTO) {

    TransactionEntity transactionEntity = new TransactionEntity();

    AccountEntity accountEntity = accountRepository
            .findById(transactionCreateRequestDTO.getAccountId())
            .orElseThrow(
                    () -> new AccountNotFoundException(
                            "Account not found with id "
                                    + transactionCreateRequestDTO.getAccountId()
                    )
            );

    if (transactionCreateRequestDTO.getDestinationAccountId() != null) {
        AccountEntity destinationAccount = accountRepository
                .findById(transactionCreateRequestDTO.getDestinationAccountId())
                .orElseThrow(
                        () -> new AccountNotFoundException(
                                "Destination account not found with id "
                                        + transactionCreateRequestDTO.getDestinationAccountId()
                        )
                );
        transactionEntity.setDestinationAccount(destinationAccount);
    }

    transactionEntity.setTransactionDate(LocalDateTime.now());
    transactionEntity.setAmount(transactionCreateRequestDTO.getAmount());
    transactionEntity.setTransactionType(transactionCreateRequestDTO.getTransactionType());
    transactionEntity.setDescription(transactionCreateRequestDTO.getDescription()    );
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


    @Transactional
    public TransactionResponseDTO processTransaction(Long transactionId) {

        TransactionEntity transactionEntity = transactionRepository
                .findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(
                        "Transaction not found with id " + transactionId));

        if (transactionEntity.getTransactionStatus() != TransactionStatus.CREATED) {
            throw new TransactionProcessingNotAllowedException(
                    "Transaction can only be processed when its status is CREATED");
        }

        AccountEntity sourceAccount = transactionEntity.getAccount();

        if (sourceAccount.getAccountStatus() != AccountStatus.ACTIVE) {
            transactionEntity.setTransactionStatus(TransactionStatus.REJECTED);
            return toResponseDTO(transactionRepository.save(transactionEntity));
        }

        TransactionType type = transactionEntity.getTransactionType();
        BigDecimal amount = transactionEntity.getAmount();

        transactionEntity.setTransactionStatus(TransactionStatus.PROCESSING);

        if (type == TransactionType.DEPOSIT) {

            sourceAccount.setBalance(
                    sourceAccount.getBalance().add(amount)
            );

            transactionEntity.setTransactionStatus(TransactionStatus.ACCEPTED);

        } else if (type == TransactionType.WITHDRAWAL) {

            if (sourceAccount.getBalance().compareTo(amount) >= 0) {

                sourceAccount.setBalance(
                        sourceAccount.getBalance().subtract(amount)
                );

                transactionEntity.setTransactionStatus(TransactionStatus.ACCEPTED);

            } else {
                transactionEntity.setTransactionStatus(TransactionStatus.REJECTED);
            }

        } else if (type == TransactionType.TRANSFER) {

            AccountEntity destinationAccount = transactionEntity.getDestinationAccount();

            if (destinationAccount == null
                    || destinationAccount.getAccountId().equals(sourceAccount.getAccountId())
                    || destinationAccount.getAccountStatus() != AccountStatus.ACTIVE) {

                transactionEntity.setTransactionStatus(TransactionStatus.REJECTED);

            } else if (sourceAccount.getBalance().compareTo(amount) >= 0) {

                // Debit the source account.
                sourceAccount.setBalance(
                        sourceAccount.getBalance().subtract(amount)
                );

                // Credit the destination account.
                destinationAccount.setBalance(
                        destinationAccount.getBalance().add(amount)
                );

                accountRepository.save(sourceAccount);
                accountRepository.save(destinationAccount);

                transactionEntity.setTransactionStatus(TransactionStatus.ACCEPTED);

            } else {
                transactionEntity.setTransactionStatus(TransactionStatus.REJECTED);
            }
        }

        accountRepository.save(sourceAccount);

        return toResponseDTO(transactionRepository.save(transactionEntity));
    }

    public Page<TransactionResponseDTO> searchTransactions(
            String firstName,
            String lastName,
            Long accountId,
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(
                        Sort.Direction.fromString(direction),
                        sortBy
                )
        );

        String normalizedFirstName = normalizeSearchParameter(firstName);
        String normalizedLastName = normalizeSearchParameter(lastName);

        return transactionRepository
                .searchTransactions(
                        normalizedFirstName,
                        normalizedLastName,
                        accountId,
                        pageable
                )
                .map(this::toResponseDTO);
    }

    private String normalizeSearchParameter(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }
}
