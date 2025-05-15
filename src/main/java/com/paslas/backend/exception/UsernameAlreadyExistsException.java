package com.paslas.backend.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String username) {
        super("Username already exist: " + username);
    }
}
