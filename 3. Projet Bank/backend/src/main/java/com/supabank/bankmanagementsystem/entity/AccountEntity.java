package com.supabank.bankmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name="account")
public class AccountEntity {
    @Id
    @Column(name="account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(length = 34,unique = true, nullable = false)
    @NotBlank
    private String iban;

    @Column(name="account_number",unique = true,nullable = false, length = 30)
    @NotBlank
    private String accountNumber ;

    private BigDecimal balance;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus accountStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

}
