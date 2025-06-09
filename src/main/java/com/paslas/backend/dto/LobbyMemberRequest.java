package com.paslas.backend.dto;

import com.paslas.backend.entity.LobbyMember;
import lombok.Data;

import java.util.UUID;

@Data
public class LobbyMemberRequest {

    private UUID userId;
    private UUID lobbyId;
    private LobbyMember.Role role;
}
