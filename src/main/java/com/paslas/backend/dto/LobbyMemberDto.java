package com.paslas.backend.dto;

import com.paslas.backend.entity.LobbyMember;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class LobbyMemberDto {

    private UUID userId;
    private String username;
    private LobbyMember.Role role;
    private LocalDateTime joinedAt;
}
