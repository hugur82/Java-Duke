package com.supabank.bankmanagementsystem.exception;

public class AccountClosureNotAllowedException extends RuntimeException {

    public AccountClosureNotAllowedException(String message) {
        super(message);
    }
}
