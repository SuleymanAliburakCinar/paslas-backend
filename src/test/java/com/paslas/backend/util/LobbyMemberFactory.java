package com.paslas.backend.util;

import com.paslas.backend.entity.Lobby;
import com.paslas.backend.entity.LobbyMember;
import com.paslas.backend.entity.User;

public class LobbyMemberFactory {

    public static LobbyMember createLobbyMember(Lobby lobby, User user) {
        LobbyMember lobbyMember = new LobbyMember();
        lobbyMember.setUser(user);
        lobbyMember.setLobby(lobby);
        return lobbyMember;
    }
}
