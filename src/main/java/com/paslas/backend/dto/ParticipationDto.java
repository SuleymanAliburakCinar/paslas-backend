package com.paslas.backend.dto;

import com.paslas.backend.entity.Participation;
import lombok.Data;

import java.time.Instant;

@Data
public class ParticipationDto {

    private long eventId;
    private String username;
    private Instant appliedAt;
    private Participation.ParticipationStatus status;
}
