package com.supabank.bankmanagementsystem.service;

import com.supabank.bankmanagementsystem.dto.EmployeeCreateRequestDTO;
import com.supabank.bankmanagementsystem.dto.EmployeeResponseDTO;
import com.supabank.bankmanagementsystem.dto.EmployeeStatusUpdateRequestDTO;
import com.supabank.bankmanagementsystem.dto.EmployeeUpdateRequestDTO;
import com.supabank.bankmanagementsystem.entity.EmployeeEntity;
import com.supabank.bankmanagementsystem.entity.EmployeeStatus;
import com.supabank.bankmanagementsystem.exception.EmployeeAlreadyExistsException;
import com.supabank.bankmanagementsystem.exception.EmployeeNotFoundException;
import com.supabank.bankmanagementsystem.repository.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<EmployeeResponseDTO> findAll() {
        return employeeRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    public EmployeeResponseDTO findById(Long employeeId) {
        return toResponseDTO(findEmployee(employeeId));
    }

    public EmployeeResponseDTO create(EmployeeCreateRequestDTO request) {
        String email = request.getEmail().trim().toLowerCase();
        if (employeeRepository.existsByEmail(email)) {
            throw new EmployeeAlreadyExistsException("An employee already exists with this email.");
        }

        EmployeeEntity employee = new EmployeeEntity();
        employee.setFirstName(request.getFirstName().trim());
        employee.setLastName(request.getLastName().trim());
        employee.setEmail(email);
        employee.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        employee.setRole(request.getRole());
        employee.setStatus(EmployeeStatus.ACTIVE);
        employee.setCreatedAt(LocalDateTime.now());

        return toResponseDTO(employeeRepository.save(employee));
    }

    public EmployeeResponseDTO updateStatus(
            Long employeeId,
            EmployeeStatusUpdateRequestDTO request
    ) {
        EmployeeEntity employee = findEmployee(employeeId);

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String currentUserEmail = authentication.getName();

        if (employee.getEmail().equalsIgnoreCase(currentUserEmail)
                && request.getStatus() == EmployeeStatus.DISABLED) {

            throw new IllegalArgumentException(
                    "You cannot disable your own account."
            );
        }

        employee.setStatus(request.getStatus());

        return toResponseDTO(employeeRepository.save(employee));
    }

    private EmployeeEntity findEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found with id " + employeeId
                ));
    }

    private EmployeeResponseDTO toResponseDTO(EmployeeEntity employee) {
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

    public EmployeeResponseDTO update(
            Long employeeId,
            EmployeeUpdateRequestDTO request
    ) {
        EmployeeEntity employee = findEmployee(employeeId);

        String email = request.getEmail().trim().toLowerCase();

        if (!employee.getEmail().equals(email)
                && employeeRepository.existsByEmail(email)) {
            throw new EmployeeAlreadyExistsException(
                    "An employee already exists with this email."
            );
        }

        employee.setFirstName(request.getFirstName().trim());
        employee.setLastName(request.getLastName().trim());
        employee.setEmail(email);
        employee.setRole(request.getRole());

        return toResponseDTO(employeeRepository.save(employee));
    }

    public String resetPassword(Long employeeId) {
        EmployeeEntity employee = findEmployee(employeeId);

        String newPassword = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12);

        employee.setPasswordHash(passwordEncoder.encode(newPassword));

        employeeRepository.save(employee);

        return newPassword;
    }
}
