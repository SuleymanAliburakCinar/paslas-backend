package com.paslas.backend.service;

import com.paslas.backend.dto.ParticipationDto;
import com.paslas.backend.entity.Event;
import com.paslas.backend.entity.Participation;
import com.paslas.backend.entity.User;
import com.paslas.backend.exception.NotFoundException;
import com.paslas.backend.mapper.ParticipationMapper;
import com.paslas.backend.repository.EventRepository;
import com.paslas.backend.repository.LobbyMemberRepository;
import com.paslas.backend.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.FormatterClosedException;
import java.util.List;
import java.util.Optional;

import static com.paslas.backend.entity.Participation.ParticipationStatus.CONFIRMED;
import static com.paslas.backend.entity.Participation.ParticipationStatus.WAITLISTED;
import static com.paslas.backend.entity.Participation.ParticipationStatus.DECLINED;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final LobbyMemberRepository lobbyMemberRepository;
    private final EventRepository eventRepository;
    private final ParticipationMapper participationMapper;

    private Participation apply(Event event, User user) {
        boolean isMember = lobbyMemberRepository.existsByUserAndLobby(user, event.getLobby());

        if (!isMember) {
            throw new FormatterClosedException();
        }

        Optional<Participation> participationOpt = participationRepository.findByEventAndUser(event, user);
        Participation participation;

        if (participationOpt.isPresent()) {
            participation = participationOpt.get();
            if (participation.getStatus() != DECLINED) {
                throw new IllegalStateException("Kullanıcı zaten bu etkinliğe kayıtlı");
            }
        } else {
            participation = Participation.builder()
                    .user(user)
                    .event(event)
                    .build();
        }

        int confirmedCount = participationRepository.countByEventAndStatus(event, CONFIRMED);
        Participation.ParticipationStatus status = confirmedCount < event.getCapacity()
                ? CONFIRMED
                : WAITLISTED;

        participation.setStatus(status);
        participation.setAppliedAt(Instant.now());

        return participationRepository.save(participation);
    }

    public ParticipationDto applyToEvent(long eventId, User user) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Etkinlik bulunamadı"));
        return participationMapper.participationToParticipationDto(apply(event, user));
    }

    private void leave(Event event, User user) {
        Participation participation = participationRepository.findByEventAndUser(event, user)
                .orElseThrow(() -> new NotFoundException("Katılım kaydı bulunamadı"));

        if (participation.getStatus() == DECLINED) {
            return;
        }

        boolean wasConfirmed = participation.getStatus() == CONFIRMED;

        participation.setStatus(DECLINED);
        participationRepository.save(participation);

        if (wasConfirmed) {
            Optional<Participation> nextInWaitlist = participationRepository
                    .findFirstByEventAndStatusOrderByAppliedAtAsc(
                            event, WAITLISTED
                    );
            nextInWaitlist.ifPresent(waitlisted -> {
                waitlisted.setStatus(CONFIRMED);
                participationRepository.save(waitlisted);
            });
        }
    }

    public void leaveEvent(long eventId, User user) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Etkinlik bulunamadı"));
        leave(event, user);
    }

    private List<ParticipationDto> getParticipationsGroupedByStatus(Event event) {
        List<Participation> participations = participationRepository.findAllByEvent(event);
        return participations.stream().map(participationMapper::participationToParticipationDto).toList();
    }

    public List<ParticipationDto> getParticipationList(long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Etkinlik bulunamadı"));
        return getParticipationsGroupedByStatus(event);
    }
}
