package com.supabank.bankmanagementsystem.exception;

public class CustomerDeletionNotAllowedException extends RuntimeException {
    public CustomerDeletionNotAllowedException(String message) {
        super(message);
    }
}
