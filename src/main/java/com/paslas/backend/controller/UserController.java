package com.paslas.backend.controller;

import com.paslas.backend.dto.LobbyResponse;
import com.paslas.backend.service.LobbyMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final LobbyMemberService lobbyMemberService;

    @GetMapping("/{userId}/lobbies")
    public ResponseEntity<List<LobbyResponse>> getUserLobbies(@PathVariable UUID userId) {

        return ResponseEntity.ok(lobbyMemberService.getLobbiesByUserId(userId));
    }
}
