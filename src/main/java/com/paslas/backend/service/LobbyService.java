package com.paslas.backend.service;

import com.paslas.backend.dto.LobbyResponseDto;
import com.paslas.backend.entity.Lobby;
import com.paslas.backend.entity.LobbyMember;
import com.paslas.backend.entity.User;
import com.paslas.backend.exception.AlreadyInLobbyException;
import com.paslas.backend.exception.LobbyNotFoundException;
import com.paslas.backend.mapper.LobbyMapper;
import com.paslas.backend.repository.LobbyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LobbyService {

    private final LobbyRepository lobbyRepository;
    private final LobbyMapper lobbyMapper;

    @Value("${application.security.join-code.charset}")
    private String charSet;
    @Value("${application.security.join-code.length}")
    private int joinCodeLength;

    @Transactional
    private Lobby saveLobby(User user, String name) {
        Lobby lobby = Lobby.builder()
                .name(name)
                .joinCode(generateUniqueJoinCode(joinCodeLength))
                .build();
        lobby.addMember(user, LobbyMember.Role.OWNER);

        return lobbyRepository.save(lobby);
    }

    @Transactional
    public LobbyResponseDto createLobby(User user, String name) {
        Lobby lobby = saveLobby(user, name);
        return lobbyMapper.lobbyToLobbyResponseDto(lobby);
    }

    public Lobby getLobbyById(UUID id){
        return lobbyRepository.findById(id)
                .orElseThrow(LobbyNotFoundException::new);
    }

    private String generateJoinCode(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charSet.length());
            sb.append(charSet.charAt(index));
        }
        return sb.toString();
    }

    private String generateUniqueJoinCode(int length) {
        String joinCode;
        do {
            joinCode = generateJoinCode(length);
        } while (lobbyRepository.existsByJoinCode(joinCode));
        return joinCode;
    }

    @Transactional
    public LobbyResponseDto joinLobby(User user, String joinCode) {
        Lobby lobby = lobbyRepository.findByJoinCode(joinCode)
                .orElseThrow(LobbyNotFoundException::new);

        if (lobby.hasMember(user)){
            throw new AlreadyInLobbyException();
        }
        lobby.addMember(user, LobbyMember.Role.PARTICIPANT);
        return lobbyMapper.lobbyToLobbyResponseDto(lobby);
    }
}
