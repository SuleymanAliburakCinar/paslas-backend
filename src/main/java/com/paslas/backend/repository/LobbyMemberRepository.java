package com.paslas.backend.repository;

import com.paslas.backend.entity.Lobby;
import com.paslas.backend.entity.LobbyMember;
import com.paslas.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LobbyMemberRepository extends JpaRepository<LobbyMember, Long> {

    List<LobbyMember> findByUserId(UUID userId);
    boolean existsByUserAndLobby(User user, Lobby lobby);
}
