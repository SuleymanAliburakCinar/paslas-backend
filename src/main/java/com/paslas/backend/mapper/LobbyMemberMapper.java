package com.paslas.backend.mapper;

import com.paslas.backend.dto.LobbyMemberDto;
import com.paslas.backend.entity.LobbyMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LobbyMemberMapper {

    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.id", target = "userId")
    LobbyMemberDto toDto(LobbyMember lobbyMember);
}
