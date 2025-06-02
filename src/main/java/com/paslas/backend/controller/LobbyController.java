package com.paslas.backend.controller;

import com.paslas.backend.dto.LobbyResponse;
import com.paslas.backend.entity.Lobby;
import com.paslas.backend.entity.User;
import com.paslas.backend.mapper.LobbyMapper;
import com.paslas.backend.security.CustomUserDetails;
import com.paslas.backend.service.LobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lobbies")
public class LobbyController {

    private final LobbyService lobbyService;
    private final LobbyMapper lobbyMapper;

    @GetMapping("/create/{name}")
    public ResponseEntity<LobbyResponse> createLobby(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable String name) {
        return ResponseEntity.ok(lobbyService.createLobby(userDetails.getUser(), name));
    }

    @GetMapping("/join/{joinCode}")
    public ResponseEntity<LobbyResponse> joinLobby(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable String joinCode) {
        return ResponseEntity.ok(lobbyService.joinLobby(userDetails.getUser(), joinCode));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LobbyResponse> getLobby(@PathVariable UUID id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Lobby lobby = lobbyService.getLobbyById(id);
        User user = userDetails.getUser();

        if(!lobby.hasMember(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bu lobby'e erişim yetkiniz yok.");
        }
        return ResponseEntity.ok(lobbyMapper.lobbyToLobbyResponseDto(lobby, user.getUsername()));
    }
}
