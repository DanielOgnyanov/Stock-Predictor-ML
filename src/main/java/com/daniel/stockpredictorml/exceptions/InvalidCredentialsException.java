package com.daniel.stockpredictorml.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException() {
        super("Invalid email or password");
    }
}
