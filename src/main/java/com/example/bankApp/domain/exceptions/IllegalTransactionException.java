package com.example.bankApp.domain.exceptions;

public class IllegalTransactionException extends RuntimeException {

    public IllegalTransactionException(String message) {
        super(message);
    }

}
