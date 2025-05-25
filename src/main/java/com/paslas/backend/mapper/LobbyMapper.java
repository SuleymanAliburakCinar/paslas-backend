package com.paslas.backend.mapper;

import com.paslas.backend.dto.LobbyResponseDto;
import com.paslas.backend.entity.Lobby;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = LobbyMemberMapper.class)
public interface LobbyMapper {

    LobbyResponseDto lobbyToLobbyResponseDto(Lobby lobby);
}
