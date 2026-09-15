package com.supabank.bankmanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class TransactionUpdateRequestDTO {

   @NotNull
   @Positive
   private BigDecimal amount;

   private String description;

}
