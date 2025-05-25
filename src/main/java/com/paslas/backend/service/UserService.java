package com.paslas.backend.service;

import com.paslas.backend.dto.UserRegistrationDto;
import com.paslas.backend.dto.UserResponseDto;
import com.paslas.backend.entity.User;
import com.paslas.backend.exception.EmailAlreadyExistsException;
import com.paslas.backend.exception.UsernameAlreadyExistsException;
import com.paslas.backend.mapper.UserMapper;
import com.paslas.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private User saveUser(UserRegistrationDto dto) {
        if (dto.getEmail() != null && userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Parolalar eşlemiyor");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(Set.of("USER"))
                .build();

        return userRepository.save(user);
    }

    public UserResponseDto createUser(UserRegistrationDto dto) {
        User user = saveUser(dto);
        return userMapper.userToUserResponseDto(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
