package com.paslas.backend.repository;

import com.paslas.backend.entity.Event;
import com.paslas.backend.entity.Participation;
import com.paslas.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    Optional<Participation> findByEventAndUser(Event event, User user);
    int countByEventAndStatus(Event event, Participation.ParticipationStatus status);
    Optional<Participation> findFirstByEventAndStatusOrderByAppliedAtAsc(Event event, Participation.ParticipationStatus status);
    List<Participation> findAllByEvent(Event event);
}
