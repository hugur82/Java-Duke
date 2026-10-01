package com.supabank.bankmanagementsystem.controller;

import com.supabank.bankmanagementsystem.dto.EmployeeResponseDTO;
import com.supabank.bankmanagementsystem.entity.EmployeeEntity;
import com.supabank.bankmanagementsystem.repository.EmployeeRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final EmployeeRepository employeeRepository;

    public AuthController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/me")
    public EmployeeResponseDTO getCurrentEmployee(Authentication authentication) {

        EmployeeEntity employee = employeeRepository
                .findByEmail(authentication.getName())
                .orElseThrow();

        return new EmployeeResponseDTO(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getRole(),
                employee.getStatus(),
                employee.getCreatedAt(),
                employee.getLastLoginAt()
        );
    }
}