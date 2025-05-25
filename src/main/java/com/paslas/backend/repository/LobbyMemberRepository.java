package com.paslas.backend.repository;

import com.paslas.backend.entity.LobbyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LobbyMemberRepository extends JpaRepository<LobbyMember, Long> {
    List<LobbyMember> findByUserId(UUID userId);
}
