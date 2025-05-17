package com.paslas.backend.service;

import com.paslas.backend.dto.UserRegistrationDto;
import com.paslas.backend.exception.EmailAlreadyExistsException;
import com.paslas.backend.exception.UsernameAlreadyExistsException;
import com.paslas.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUserShouldThrowExceptionWhenEmailExist() {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setEmail("abc");

        when(userRepository.existsByEmail(registrationDto.getEmail())).thenReturn(true);

        EmailAlreadyExistsException ex = assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(registrationDto));
        assertEquals("Email already exist: " + registrationDto.getEmail(), ex.getMessage());
    }

    @Test
    void createUserShouldThrowExceptionWhenUsernameExist() {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setUsername("abc");

        when(userRepository.existsByUsername(registrationDto.getUsername())).thenReturn(true);

        UsernameAlreadyExistsException ex = assertThrows(UsernameAlreadyExistsException.class, () -> userService.createUser(registrationDto));
        assertEquals("Username already exist: " + registrationDto.getUsername(), ex.getMessage());
    }

    @Test
    void createUserShouldThrowExceptionWhenPasswordDidNotMatch() {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setPassword("abc");
        registrationDto.setConfirmPassword("abcd");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> userService.createUser(registrationDto));
        assertEquals("Parolalar eşlemiyor", ex.getMessage());
    }
}
