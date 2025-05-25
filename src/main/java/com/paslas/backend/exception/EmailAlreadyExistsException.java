package com.paslas.backend.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException() {
        super("Bu email zaten kullanılıyor");
    }
}
