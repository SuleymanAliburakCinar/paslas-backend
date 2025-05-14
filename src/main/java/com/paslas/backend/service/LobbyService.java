package com.paslas.backend.service;

import com.paslas.backend.entity.Lobby;
import com.paslas.backend.repository.LobbyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LobbyService {

    private LobbyRepository lobbyRepository;

    public Lobby saveLobby(String name) {
        Lobby lobby = new Lobby(name);
        return lobbyRepository.save(lobby);
    }
}
