package com.supabank.bankmanagementsystem.dto;

import com.supabank.bankmanagementsystem.entity.CustomerStatus;

import java.time.LocalDate;

public class CustomerResponseDTO {

    public final Long customerId;
    public final String firstName;
    public final String lastName;
    public final String email;
    public final String phone;
    public final LocalDate birthDate;
    public final String address;
    public final String postalCode;
    public final String city;
    public final CustomerStatus status;

    public CustomerResponseDTO(Long customerId, String firstName, String lastName, String email, String phone, LocalDate birthDate, String address, String postalCode, String city, CustomerStatus status) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.status = status;
    }
}
