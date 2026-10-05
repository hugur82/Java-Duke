package com.supabank.bankmanagementsystem.controller;

import com.supabank.bankmanagementsystem.dto.ChangePasswordRequestDTO;
import com.supabank.bankmanagementsystem.dto.EmployeeResponseDTO;
import com.supabank.bankmanagementsystem.dto.EmployeeUpdateRequestDTO;
import com.supabank.bankmanagementsystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final EmployeeService employeeService;

    public ProfileController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PutMapping
    public EmployeeResponseDTO updateProfile(
            Authentication authentication,
            @RequestBody @Valid EmployeeUpdateRequestDTO request
    ) {
        return employeeService.updateProfile(
                authentication.getName(),
                request
        );
    }

    @PutMapping("/password")
    public void changePassword(
            Authentication authentication,
            @RequestBody @Valid ChangePasswordRequestDTO request
    ) {
        employeeService.changePassword(
                authentication.getName(),
                request
        );
    }
}