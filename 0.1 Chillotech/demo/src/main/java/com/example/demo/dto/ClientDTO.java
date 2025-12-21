package com.example.demo.dto;

import jakarta.persistence.Column;

public record ClientDTO(
        Integer id,
        String email,
        String telephone
) {
}
