package com.booker.shared.exception.models;

public class BookerException extends RuntimeException {

    public BookerException(String message) {
        super(message);
    }

    public BookerException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookerException() {

    }
}
