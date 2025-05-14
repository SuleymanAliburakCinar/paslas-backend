package com.paslas.backend.controller;

import com.paslas.backend.entity.Lobby;
import com.paslas.backend.service.LobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lobbies")
public class LobbyController {

    private LobbyService lobbyService;

    @PostMapping("/create/{name}")
    public ResponseEntity<Lobby> createLobby(@PathVariable String name){
        Lobby createdLobby = lobbyService.saveLobby(name);
        return ResponseEntity.ok(createdLobby);
    }
}
