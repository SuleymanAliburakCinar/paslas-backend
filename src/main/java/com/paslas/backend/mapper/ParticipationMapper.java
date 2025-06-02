package com.paslas.backend.mapper;

import com.paslas.backend.dto.ParticipationDto;
import com.paslas.backend.entity.Participation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParticipationMapper {

    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "event.id", target = "eventId")
    ParticipationDto participationToParticipationDto(Participation participation);
}
