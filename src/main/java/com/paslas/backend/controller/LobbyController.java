package com.paslas.backend.controller;

import com.paslas.backend.dto.LobbyResponseDto;
import com.paslas.backend.entity.Lobby;
import com.paslas.backend.mapper.LobbyMapper;
import com.paslas.backend.security.CustomUserDetails;
import com.paslas.backend.service.LobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lobbies")
public class LobbyController {

    private final LobbyService lobbyService;
    private final LobbyMapper lobbyMapper;

    @GetMapping("/create/{name}")
    public ResponseEntity<LobbyResponseDto> createLobby(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable String name) {
        return ResponseEntity.ok(lobbyService.createLobby(userDetails.getUser(), name));
    }

    @GetMapping("/join/{joinCode}")
    public ResponseEntity<LobbyResponseDto> joinLobby(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable String joinCode) {
        return ResponseEntity.ok(lobbyService.joinLobby(userDetails.getUser(), joinCode));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LobbyResponseDto> getLobby(@PathVariable UUID id) {
        Lobby lobby = lobbyService.getLobbyById(id);
        return ResponseEntity.ok(lobbyMapper.lobbyToLobbyResponseDto(lobby));
    }
}
