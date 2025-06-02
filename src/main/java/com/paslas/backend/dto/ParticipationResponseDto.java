package com.paslas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ParticipationResponseDto {

    private List<ParticipationDto> confirmedParticipation;
    private List<ParticipationDto> waitlistedParticipation;
    private List<ParticipationDto> declinedParticipation;
}
