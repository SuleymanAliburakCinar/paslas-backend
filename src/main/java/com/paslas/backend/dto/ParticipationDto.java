package com.paslas.backend.dto;

import com.paslas.backend.entity.Participation;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ParticipationDto {

    private UUID userId;
    private String username;
    private Instant appliedAt;
    private Participation.ParticipationStatus status;
}
