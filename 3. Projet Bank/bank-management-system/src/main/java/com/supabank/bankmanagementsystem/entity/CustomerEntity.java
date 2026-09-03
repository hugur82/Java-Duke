package com.supabank.bankmanagementsystem.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "customer_id")
    private Long customerId;

    @Column (name = "first_name")
    private String firstName;

    @Column (name = "last_name")
    private String lastName;

    @Column(name ="date_of_birth")
    private LocalDate birthDate;

    private String phone;

    @Column(nullable = false,unique = true,length = 150)
    private String email;

    @Column(name = "password_hash",nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String address;

    @Column(name = "postal_code")
    private String postalCode;

    private String city;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;
}
