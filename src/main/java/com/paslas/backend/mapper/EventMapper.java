package com.paslas.backend.mapper;

import com.paslas.backend.dto.EventRequest;
import com.paslas.backend.dto.EventResponse;
import com.paslas.backend.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface EventMapper {

    @Mapping(source = "createdBy.username", target = "createdBy")
    EventResponse eventToEventResponse(Event event);

    @Mapping(source = "lobbyId", target = "lobby.id")
    @Mapping(source = "userId", target = "createdBy.id")
    Event eventRequestToEvent(EventRequest eventRequest);
}
