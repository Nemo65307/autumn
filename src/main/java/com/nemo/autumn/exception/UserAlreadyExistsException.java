package com.nemo.autumn.exception;

public class UserAlreadyExistsException extends ValidationException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}