package com.supabank.bankmanagementsystem.controller;

import com.supabank.bankmanagementsystem.dto.EmployeeCreateRequestDTO;
import com.supabank.bankmanagementsystem.dto.EmployeeResponseDTO;
import com.supabank.bankmanagementsystem.dto.EmployeeStatusUpdateRequestDTO;
import com.supabank.bankmanagementsystem.dto.EmployeeUpdateRequestDTO;
import com.supabank.bankmanagementsystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeResponseDTO> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public EmployeeResponseDTO findById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @PostMapping
    public EmployeeResponseDTO create(@RequestBody @Valid EmployeeCreateRequestDTO request) {
        return employeeService.create(request);
    }

    @PostMapping("/{id}/reset-password")
    public String resetPassword(@PathVariable Long id) {
        return employeeService.resetPassword(id);
    }

    @PatchMapping("/{id}/status")
    public EmployeeResponseDTO updateStatus(
            @PathVariable Long id,
            @RequestBody @Valid EmployeeStatusUpdateRequestDTO request
    ) {
        return employeeService.updateStatus(id, request);
    }

    @PutMapping("/{id}")
    public EmployeeResponseDTO update(
            @PathVariable Long id,
            @RequestBody @Valid EmployeeUpdateRequestDTO request
    ) {
        return employeeService.update(id, request);
    }

}
