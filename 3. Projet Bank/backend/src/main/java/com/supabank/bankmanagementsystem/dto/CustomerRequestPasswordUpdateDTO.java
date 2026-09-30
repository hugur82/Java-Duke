package com.supabank.bankmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequestPasswordUpdateDTO {

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;
}