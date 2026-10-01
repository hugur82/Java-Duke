package com.supabank.bankmanagementsystem.dto;

import com.supabank.bankmanagementsystem.entity.EmployeeRole;
import com.supabank.bankmanagementsystem.entity.EmployeeStatus;

import java.time.LocalDateTime;

public record EmployeeResponseDTO(
        Long employeeId,
        String firstName,
        String lastName,
        String email,
        EmployeeRole role,
        EmployeeStatus status,
        LocalDateTime createdAt,
        LocalDateTime lastLoginAt
) {
}
