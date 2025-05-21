package com.paslas.backend.controller;

import com.paslas.backend.service.LobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lobbies")
public class LobbyController {

    private final LobbyService lobbyService;

    @GetMapping("/dummy")
    public ResponseEntity<String> dummyEndpoint(){
        return ResponseEntity.ok("Authentication success");
    }
}
