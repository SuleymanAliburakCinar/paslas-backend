package com.paslas.backend.controller;

import com.paslas.backend.dto.EventRequest;
import com.paslas.backend.dto.EventResponse;
import com.paslas.backend.security.CustomUserDetails;
import com.paslas.backend.service.EventService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody @Valid EventRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        request.setCreatedBy(userDetails.getUser());
        return ResponseEntity.ok(eventService.createEvent(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable @NotNull long id) {
        return ResponseEntity.ok(eventService.getById(id));
    }

    @GetMapping("/getAll/{lobbyId}")
    public ResponseEntity<List<EventResponse>> getAllByLobbyId(@PathVariable @NotNull UUID lobbyId) {
        return ResponseEntity.ok(eventService.getAll(lobbyId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable @NotNull long id, @RequestBody EventRequest request) {
        return ResponseEntity.ok(eventService.updateById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable long id) {
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}