package com.example.bankApp.domain.exceptions;

public class IllegalTransactionException extends Exception {

    public IllegalTransactionException(String message) {
        super(message);
    }

}
