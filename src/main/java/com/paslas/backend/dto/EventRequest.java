package com.paslas.backend.dto;

import com.paslas.backend.entity.Event;
import com.paslas.backend.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EventRequest {

    @NotNull
    private UUID lobbyId;

    private User createdBy;

    @NotNull
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private LocalDateTime eventTime;

    @NotNull
    @Min(value = 1, message = "Kapasite 0'dan büyük olmalıdır.")
    private int capacity;

    private Event.EventStatus status = Event.EventStatus.OPEN;
}
