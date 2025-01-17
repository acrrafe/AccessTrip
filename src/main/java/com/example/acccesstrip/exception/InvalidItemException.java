package com.example.acccesstrip.exception;

public class InvalidItemException extends RuntimeException {

    public InvalidItemException() {
        super();
    }

    public InvalidItemException(String message) {
        super(message);
    }

    public InvalidItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidItemException(Throwable cause) {
        super(cause);
    }
}
