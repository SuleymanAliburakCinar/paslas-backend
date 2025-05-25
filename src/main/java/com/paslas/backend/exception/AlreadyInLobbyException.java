package com.paslas.backend.exception;

public class AlreadyInLobbyException extends RuntimeException {

    public AlreadyInLobbyException() {
        super("User already in the lobby");
    }
}
