package com.paslas.backend.service;

import com.paslas.backend.dto.EventRequest;
import com.paslas.backend.dto.EventResponse;
import com.paslas.backend.entity.Event;
import com.paslas.backend.entity.Lobby;
import com.paslas.backend.exception.NotFoundException;
import com.paslas.backend.mapper.EventMapper;
import com.paslas.backend.repository.EventRepository;
import com.paslas.backend.repository.LobbyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final LobbyRepository lobbyRepository;
    private final EventMapper eventMapper;

    private Event save(Event event) {
        return eventRepository.save(event);
    }

    public EventResponse createEvent(EventRequest request) {
        Event event = save(createEventFromRequest(request));
        return eventMapper.eventToEventResponse(event);
    }

    private Event createEventFromRequest(EventRequest request) {
        Lobby lobby = lobbyRepository.findById(request.getLobbyId())
                .orElseThrow(() -> new NotFoundException("Lobby bulunamadı"));

        Event event = eventMapper.eventRequestToEvent(request);
        event.setLobby(lobby);
        return event;
    }

    private Event findById(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("İlgili event bulunamadı"));
    }

    public EventResponse getById(long id) {
        return eventMapper.eventToEventResponse(findById(id));
    }

    public List<EventResponse> getAll(UUID lobbyId){
        return eventRepository.findAllByLobbyId(lobbyId).stream()
                .map(eventMapper::eventToEventResponse)
                .toList();
    }

    @Transactional
    public EventResponse updateById(long id, EventRequest request) {
        Event event = findById(id);
        updateEventAsRequest(event, request);

        event = save(event);
        return eventMapper.eventToEventResponse(event);
    }

    @Transactional
    public void deleteById(long id) {
        if (!eventRepository.existsById(id)) {
            throw new NotFoundException("İlgili event bulunamadığı için silme işlemi gerçekleştirilemez.");
        }
        eventRepository.deleteById(id);
    }

    private void updateEventAsRequest(Event event, EventRequest request) {
        event.setName(request.getName());
        event.setCapacity(request.getCapacity());
        event.setEventTime(request.getEventTime());

        if (request.getStatus() != null) {
            event.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            event.setStatus(request.getStatus());
        }
    }
}
