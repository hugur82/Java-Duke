package com.supabank.bankmanagementsystem.service;

import com.supabank.bankmanagementsystem.dto.AccountCreateRequestDTO;
import com.supabank.bankmanagementsystem.dto.AccountResponseDTO;
import com.supabank.bankmanagementsystem.dto.AccountUpdateRequestDTO;
import com.supabank.bankmanagementsystem.entity.AccountEntity;
import com.supabank.bankmanagementsystem.entity.AccountStatus;
import com.supabank.bankmanagementsystem.entity.CustomerEntity;
import com.supabank.bankmanagementsystem.exception.AccountNotFoundException;
import com.supabank.bankmanagementsystem.exception.CustomerNotFoundException;
import com.supabank.bankmanagementsystem.repository.AccountRepository;
import com.supabank.bankmanagementsystem.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {
    private  final AccountRepository accountRepository;
    private  final CustomerRepository customerRepository;


    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public List<AccountResponseDTO> getAllAccounts(){
        return accountRepository
                .findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public AccountResponseDTO createAccount(AccountCreateRequestDTO accountCreateRequestDTO) {
        AccountEntity accountEntity = new AccountEntity();
        CustomerEntity customerEntity = customerRepository.findById(accountCreateRequestDTO.getCustomerId()).orElseThrow(()->new CustomerNotFoundException("Customer not found with id "+ accountCreateRequestDTO.getCustomerId()));

        accountEntity.setCustomer(customerEntity);
        accountEntity.setAccountType(accountCreateRequestDTO.getAccountType());
        accountEntity.setCreationDate(LocalDateTime.now());
        accountEntity.setBalance(BigDecimal.ZERO);
        accountEntity.setAccountStatus(AccountStatus.ACTIVE);

        String accountNumber = generateUniqueAccountNumber();

        accountEntity.setAccountNumber(accountNumber);
        accountEntity.setIban(generateIban(accountNumber));

        return toResponseDTO(accountRepository.save(accountEntity));
    }

    private AccountResponseDTO toResponseDTO(AccountEntity account) {
        return new AccountResponseDTO(account.getAccountId(),account.getIban(),account.getAccountNumber(),account.getBalance(),account.getCreationDate(),account.getAccountType(),account.getAccountStatus(), account.getCustomer().getCustomerId());
    }

    private String generateUniqueAccountNumber() {
        StringBuilder sb = new StringBuilder();
        do {
            sb.setLength(0);
            int[] arrNumber = new int[10];
            for (int i = 0; i < 10; i++) {
                arrNumber[i] = (int) (Math.random() * 10);
            }
            for (int arr : arrNumber) {
                sb.append(arr);
            }
        }while (accountRepository.existsByAccountNumber(sb.toString()));
        return sb.toString();
    }

    private String generateIban(String accountNumber){
        // BBAN suisse :
        // 5 chiffres de clearing + 12 caractères de numéro de compte
        String bban = "00000" + String.format("%012d", Long.parseLong(accountNumber));

        // Déplacement de CH00 à la fin
        String rearranged = bban + "172700";

        // Calcul de la clé de contrôle MOD-97
        int remainder = 0;

        for (char c : rearranged.toCharArray()) {
            remainder = (remainder * 10 + (c - '0')) % 97;
        }

        int checkDigits = 98 - remainder;

        return String.format("CH%02d%s", checkDigits, bban);
    }

    public void deleteAccountById(Long accountId) {
        AccountEntity accountEntity = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account not found with id " + accountId));
        accountRepository.delete(accountEntity);
    }

    public AccountResponseDTO findAccountById(Long accountId) {
        AccountEntity accountEntity = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account not found with id " + accountId));
        return toResponseDTO(accountEntity);
    }

    public AccountResponseDTO updateAccount(Long accountId,AccountUpdateRequestDTO accountUpdateRequestDTO) {
        AccountEntity accountEntity = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(
                        "Account not found with id " + accountId));

        accountEntity.setAccountStatus(accountUpdateRequestDTO.getStatus());

        return toResponseDTO(accountRepository.save(accountEntity));
    }
}
