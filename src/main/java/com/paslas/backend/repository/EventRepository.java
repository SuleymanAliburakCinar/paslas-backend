package com.paslas.backend.repository;

import com.paslas.backend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsById(long id);
    List<Event> findAllByLobbyId(UUID lobbyId);
}