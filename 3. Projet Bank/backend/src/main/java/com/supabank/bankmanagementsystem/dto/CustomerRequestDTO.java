package com.supabank.bankmanagementsystem.dto;

import com.supabank.bankmanagementsystem.entity.CustomerStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerRequestDTO {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private LocalDate birthDate;
    private String phone;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    private String address;
    private String postalCode;
    private String city;
    @NotNull
    private CustomerStatus status;



}
