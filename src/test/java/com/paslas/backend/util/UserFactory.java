package com.paslas.backend.util;

import com.paslas.backend.entity.User;

public class UserFactory {

    public static User createUserWithUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return user;
    }
}
