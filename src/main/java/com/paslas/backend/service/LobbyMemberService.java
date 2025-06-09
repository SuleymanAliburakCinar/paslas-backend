package com.paslas.backend.service;

import com.paslas.backend.dto.LobbyMemberDto;
import com.paslas.backend.dto.LobbyMemberRequest;
import com.paslas.backend.dto.LobbyResponse;
import com.paslas.backend.entity.Lobby;
import com.paslas.backend.entity.LobbyMember;
import com.paslas.backend.entity.User;
import com.paslas.backend.exception.NotFoundException;
import com.paslas.backend.mapper.LobbyMapper;
import com.paslas.backend.mapper.LobbyMemberMapper;
import com.paslas.backend.repository.LobbyMemberRepository;
import com.paslas.backend.repository.LobbyRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LobbyMemberService {

    private final LobbyMemberRepository lobbyMemberRepository;
    private final LobbyRepository lobbyRepository;
    private final LobbyMemberMapper lobbyMemberMapper;
    private final LobbyMapper lobbyMapper;

    public List<LobbyResponse> getLobbiesByUserId(UUID id) {
        List<LobbyMember> member = lobbyMemberRepository.findByUserId(id);
        return member.stream().map(LobbyMember::getLobby).map(lobbyMapper::lobbyToLobbyResponseDto).toList();
    }

    public LobbyMemberDto updateLobbyMemberRole(LobbyMemberRequest request) {
        LobbyMember lobbyMember = lobbyMemberRepository.findByUserIdAndLobbyId(request.getUserId(), request.getLobbyId())
                .orElseThrow(() -> new NotFoundException("Üye bulunamadı"));
        lobbyMember.setRole(request.getRole());
        lobbyMemberRepository.save(lobbyMember);
        return lobbyMemberMapper.toDto(lobbyMember);
    }

    public boolean isOwner(User user, UUID lobbyId) {
        Lobby lobby = lobbyRepository.findById(lobbyId)
                .orElseThrow(() -> new NotFoundException("Lobi bulunamadı"));
        return lobbyMemberRepository.existsByUserAndLobbyAndRole(user, lobby, LobbyMember.Role.OWNER);
    }
}
