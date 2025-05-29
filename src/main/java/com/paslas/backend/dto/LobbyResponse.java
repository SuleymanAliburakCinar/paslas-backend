package com.paslas.backend.dto;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class LobbyResponse {

    private UUID id;
    private String name;
    private Set<LobbyMemberDto> members;
}
