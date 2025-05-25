package com.paslas.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "lobbies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Lobby extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String name;

    @Column(unique = true)
    private String joinCode;

    @Builder.Default
    @OneToMany(
            mappedBy = "lobby",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<LobbyMember> members = new HashSet<>();

    public void addMember(User user, LobbyMember.Role role) {
        LobbyMember lobbyMember = LobbyMember.builder()
                .lobby(this)
                .user(user)
                .role(role)
                .build();
        members.add(lobbyMember);
    }

    public void removeMember(User user) {
        members.removeIf(member -> member.getUser().equals(user));
    }

    public boolean hasMember(User user) {
        return members.stream().anyMatch(m -> m.getUser().getId().equals(user.getId()));
    }
}
