package com.supabank.bankmanagementsystem.exception;

public class TransactionUpdateNotAllowedException extends RuntimeException {
    public TransactionUpdateNotAllowedException(String message) {
        super(message);
    }
}
