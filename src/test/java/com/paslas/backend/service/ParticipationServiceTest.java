package com.paslas.backend.service;

import com.paslas.backend.entity.Event;
import com.paslas.backend.entity.Lobby;
import com.paslas.backend.entity.Participation;
import com.paslas.backend.entity.User;
import com.paslas.backend.exception.NotFoundException;
import com.paslas.backend.mapper.ParticipationMapper;
import com.paslas.backend.repository.EventRepository;
import com.paslas.backend.repository.LobbyMemberRepository;
import com.paslas.backend.repository.ParticipationRepository;
import com.paslas.backend.util.EventFactory;
import com.paslas.backend.util.LobbyFactory;
import com.paslas.backend.util.ParticipationFactory;
import com.paslas.backend.util.UserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.FormatterClosedException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParticipationServiceTest {

    @Mock
    private ParticipationRepository participationRepository;
    @Mock
    private LobbyMemberRepository lobbyMemberRepository;
    @Mock
    private EventRepository eventRepository;

    @Spy
    private ParticipationMapper participationMapper;

    @InjectMocks
    private ParticipationService participationService;

    @Test
    void applyToEventShouldThrowExceptionWhenEventDoesNotExist() {
        User user = UserFactory.createUserWithUsername("");
        long id = 0;
        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> participationService.applyToEvent(id, user));
        assertEquals("Etkinlik bulunamadı", ex.getMessage());
    }

    @Test
    void applyToEventShouldThrowExceptionWhenUserIsNotMember() {
        User user = UserFactory.createUserWithUsername("");
        Event event = EventFactory.createEventWithName("");
        Lobby lobby = LobbyFactory.createLobby();
        event.setLobby(lobby);
        long id = 0;

        when(eventRepository.findById(id)).thenReturn(Optional.of(event));
        when(lobbyMemberRepository.existsByUserAndLobby(user, lobby)).thenReturn(false);

        assertThrows(FormatterClosedException.class, () -> participationService.applyToEvent(id, user));
    }

    @Test
    void applyToEventShouldThrowExceptionWhenExistParticipationStatusIsNotDeclined() {
        User user = UserFactory.createUserWithUsername("");
        Event event = EventFactory.createEventWithName("");
        Lobby lobby = LobbyFactory.createLobby();
        Participation participation = ParticipationFactory.createStatusConfirmedParticipation();
        event.setLobby(lobby);
        long id = 0;

        when(eventRepository.findById(id)).thenReturn(Optional.of(event));
        when(lobbyMemberRepository.existsByUserAndLobby(user, lobby)).thenReturn(true);
        when(participationRepository.findByEventAndUser(event, user)).thenReturn(Optional.of(participation));

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> participationService.applyToEvent(id, user));
        assertEquals("Kullanıcı zaten bu etkinliğe kayıtlı", ex.getMessage());
    }

    @Test
    void applyToEventShouldNotThrowExceptionWhenExistParticipationStatusIsDeclined() {
        User user = UserFactory.createUserWithUsername("");
        Event event = EventFactory.createEventWithName("");
        Lobby lobby = LobbyFactory.createLobby();
        Participation participation = ParticipationFactory.createStatusDeclinedParticipation();
        event.setLobby(lobby);
        long id = 0;

        when(eventRepository.findById(id)).thenReturn(Optional.of(event));
        when(lobbyMemberRepository.existsByUserAndLobby(user, lobby)).thenReturn(true);
        when(participationRepository.findByEventAndUser(event, user)).thenReturn(Optional.of(participation));

        assertDoesNotThrow(() -> participationService.applyToEvent(id, user));
    }

    /*@Test
    void applyToEventShouldReturnConfirmedStatusParticipationWhenConfirmedCountLessThanCapacity() {
        long id = 0;
        int capacity = 20;
        int confirmedCount = 2;
        User user = UserFactory.createUserWithUsername("username");
        Event event = EventFactory.createEventWithName("");
        Lobby lobby = LobbyFactory.createLobby();
        lobby.setId(UUID.randomUUID());
        event.setLobby(lobby);
        event.setCapacity(capacity);

        when(eventRepository.findById(id)).thenReturn(Optional.of(event));
        when(lobbyMemberRepository.existsByUserAndLobby(user, lobby)).thenReturn(true);
        when(participationRepository.findByEventAndUser(event, user)).thenReturn(Optional.empty());
        when(participationRepository.countByEventAndStatus(event, Participation.ParticipationStatus.CONFIRMED)).thenReturn(confirmedCount);

        Participation participation = Participation.builder()
                .event(event)
                .user(user)
                .status(Participation.ParticipationStatus.CONFIRMED)
                .appliedAt(Instant.now())
                .build();

        when(participationRepository.save(any(Participation.class))).thenReturn(participation);

        ParticipationDto result = participationService.applyToEvent(id, user);
        assertEquals(Participation.ParticipationStatus.CONFIRMED, result.getStatus());
    }*/
}
