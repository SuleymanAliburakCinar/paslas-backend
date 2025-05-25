package com.paslas.backend.exception;

public class LobbyNotFoundException extends RuntimeException {

    public LobbyNotFoundException() {
        super("Invalid join code");
    }
}
