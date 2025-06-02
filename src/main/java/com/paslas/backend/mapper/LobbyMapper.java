package com.paslas.backend.mapper;

import com.paslas.backend.dto.LobbyResponse;
import com.paslas.backend.entity.Lobby;
import com.paslas.backend.entity.LobbyMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = LobbyMemberMapper.class)
public interface LobbyMapper {

    LobbyResponse lobbyToLobbyResponseDto(Lobby lobby);

    @Mapping(target = "currentUserRole", expression = "java(getCurrentUserRole(lobby, currentUsername))")
    LobbyResponse lobbyToLobbyResponseDto(Lobby lobby, String currentUsername);

    default LobbyMember.Role getCurrentUserRole(Lobby lobby, String currentUsername) {
        return lobby.getMembers().stream()
                .filter(lobbyMember -> lobbyMember.getUser().getUsername().equals(currentUsername))
                .map(LobbyMember::getRole)
                .findFirst()
                .orElse(null);
    }
}
