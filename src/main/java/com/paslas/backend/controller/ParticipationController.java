package com.paslas.backend.controller;

import com.paslas.backend.dto.ParticipationDto;
import com.paslas.backend.dto.ParticipationResponseDto;
import com.paslas.backend.entity.User;
import com.paslas.backend.security.CustomUserDetails;
import com.paslas.backend.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/event/")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    @GetMapping("/apply/{eventId}")
    public ResponseEntity<ParticipationDto> applyEvent(@PathVariable long eventId, @AuthenticationPrincipal CustomUserDetails userDetails){
        User user = userDetails.getUser();
        return ResponseEntity.ok(participationService.applyToEvent(eventId, user));
    }

    @GetMapping("/leave/{eventId}")
    public ResponseEntity<Void> leaveEvent(@PathVariable long eventId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        participationService.leaveEvent(eventId, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/participations/{eventId}")
    public ResponseEntity<ParticipationResponseDto> getParticipations(@PathVariable long eventId) {
        return ResponseEntity.ok(participationService.getParticipationList(eventId));
    }
}
