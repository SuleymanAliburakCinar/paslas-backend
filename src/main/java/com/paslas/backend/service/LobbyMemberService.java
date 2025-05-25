package com.paslas.backend.service;

import com.paslas.backend.dto.LobbyResponseDto;
import com.paslas.backend.entity.LobbyMember;
import com.paslas.backend.mapper.LobbyMapper;
import com.paslas.backend.repository.LobbyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LobbyMemberService {

    private final LobbyMemberRepository lobbyMemberRepository;
    private final LobbyMapper lobbyMapper;

    public List<LobbyResponseDto> getLobbiesByUserId(UUID id) {
        List<LobbyMember> member = lobbyMemberRepository.findByUserId(id);
        return member.stream().map(LobbyMember::getLobby).map(lobbyMapper::lobbyToLobbyResponseDto).toList();
    }
}
