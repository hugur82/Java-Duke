package com.supabank.bankmanagementsystem.exception;

public class AccountDeletionNotAllowedException extends RuntimeException {
    public AccountDeletionNotAllowedException(String message) {
        super(message);
    }
}
