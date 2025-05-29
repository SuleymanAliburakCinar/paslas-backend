package com.paslas.backend.mapper;

import com.paslas.backend.dto.LobbyResponse;
import com.paslas.backend.entity.Lobby;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = LobbyMemberMapper.class)
public interface LobbyMapper {

    LobbyResponse lobbyToLobbyResponseDto(Lobby lobby);
}
