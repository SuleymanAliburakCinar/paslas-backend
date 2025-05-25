package com.paslas.backend.repository;

import com.paslas.backend.entity.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LobbyRepository extends JpaRepository<Lobby, UUID> {

    boolean existsByJoinCode(String joinCode);
    Optional<Lobby> findByJoinCode(String joinCode);
}
