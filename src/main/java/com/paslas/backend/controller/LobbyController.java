package com.paslas.backend.controller;

import com.paslas.backend.service.LobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lobbies")
public class LobbyController {

    private final LobbyService lobbyService;
}
