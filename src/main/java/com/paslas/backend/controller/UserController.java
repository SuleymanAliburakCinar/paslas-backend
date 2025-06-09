package com.paslas.backend.controller;

import com.paslas.backend.dto.LobbyMemberDto;
import com.paslas.backend.dto.LobbyMemberRequest;
import com.paslas.backend.dto.LobbyResponse;
import com.paslas.backend.entity.User;
import com.paslas.backend.exception.NotFoundException;
import com.paslas.backend.security.CustomUserDetails;
import com.paslas.backend.service.LobbyMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public ResponseEntity<LobbyMemberDto> updateLobbyMemberRole(@RequestBody LobbyMemberRequest request, @AuthenticationPrincipal CustomUserDetails userDetails){
        User user = userDetails.getUser();
        if(!lobbyMemberService.isOwner(user, request.getLobbyId())) {
            throw new NotFoundException("Yetkilendirme işlemi sadece lobi sahibi tarafından yapılabilir");
        }
        return ResponseEntity.ok(lobbyMemberService.updateLobbyMemberRole(request));
    }
}
