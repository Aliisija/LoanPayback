package com.example.loanpayback.exception;

public class UnsupportedOperationException extends RuntimeException {

    final String message;

    public UnsupportedOperationException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
