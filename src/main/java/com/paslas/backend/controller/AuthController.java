package com.paslas.backend.controller;

import com.paslas.backend.dto.AuthenticationRequest;
import com.paslas.backend.dto.LoginResponse;
import com.paslas.backend.dto.UserRegistrationDto;
import com.paslas.backend.dto.UserResponseDto;
import com.paslas.backend.service.AuthService;
import com.paslas.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRegistrationDto dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        LoginResponse authResponse = authService.login(request);
        response.addCookie(authService.createHttpOnlySecureCookieWithToken(authResponse.token(), request.rememberMe()));
        return ResponseEntity.ok(authResponse.user());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        response.addCookie(authService.createEmptyCookie());
        return ResponseEntity.ok("Çıkış başarılı");
    }

    @GetMapping("/authMe")
    public ResponseEntity<UserResponseDto> authMe(HttpServletRequest request) {
        return ResponseEntity.ok(authService.authUser(request));
    }
}
