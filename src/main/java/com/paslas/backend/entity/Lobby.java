package com.paslas.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lobby {

    @Id
    private String id;
    private LocalDateTime createdAt;
    private String name;

    public Lobby(String name) {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.name = name;
    }
}
