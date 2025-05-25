package com.paslas.backend.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException() {
        super("Bu kullanıcı adı kullanılıyor");
    }
}
