package com.aelyashevich.notion.exception;

public class InvalidJWTTokenException extends RuntimeException {
    public InvalidJWTTokenException() {
    }

    public InvalidJWTTokenException(String message) {
        super(message);
    }
}
