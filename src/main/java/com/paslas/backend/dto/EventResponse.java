package com.paslas.backend.dto;

import com.paslas.backend.entity.Event;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventResponse {

    private long id;
    private String name;
    private String description;
    private int capacity;
    private LocalDateTime eventTime;
    private String createdBy;
    private LocalDateTime createdAt;
    private Event.EventStatus status;
}
