package com.supabank.bankmanagementsystem.dto;

import com.supabank.bankmanagementsystem.entity.EmployeeStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeStatusUpdateRequestDTO {

    @NotNull
    private EmployeeStatus status;
}
