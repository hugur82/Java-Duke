package com.supabank.bankmanagementsystem.exception;

public class TransactionProcessingNotAllowedException extends RuntimeException {
    public TransactionProcessingNotAllowedException(String message) {
        super(message);
    }
}
