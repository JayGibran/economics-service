package com.economics.exception;

public class UserServiceException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public UserServiceException(String message) {
        super(message);
    }
}