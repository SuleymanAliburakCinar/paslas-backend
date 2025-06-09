package com.paslas.backend.dto;

import com.paslas.backend.entity.LobbyMember;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class LobbyResponse {

    private UUID id;
    private String name;
    private String joinCode;
    private Set<LobbyMemberDto> members;
    private LobbyMember.Role currentUserRole;
}
