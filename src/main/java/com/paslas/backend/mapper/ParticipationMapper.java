package com.paslas.backend.mapper;

import com.paslas.backend.dto.ParticipationDto;
import com.paslas.backend.entity.Participation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParticipationMapper {

    ParticipationDto participationToParticipationDto(Participation participation);
}
