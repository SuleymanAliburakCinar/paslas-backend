package com.paslas.backend.service;

import com.paslas.backend.dto.AuthenticationRequest;
import com.paslas.backend.dto.AuthenticationResponse;
import com.paslas.backend.entity.User;
import com.paslas.backend.repository.UserRepository;
import com.paslas.backend.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException(request.username()));

        final String accessToken = jwtService.generateToken(user);

        return new AuthenticationResponse(accessToken);
    }
}
