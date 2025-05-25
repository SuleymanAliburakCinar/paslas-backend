package com.paslas.backend.dto;

import com.paslas.backend.entity.LobbyMember;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LobbyMemberDto {

    private String username;
    private LobbyMember.Role role;
    private LocalDateTime joinedAt;
}
