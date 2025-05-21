package com.paslas.backend.service;

import com.paslas.backend.dto.AuthenticationRequest;
import com.paslas.backend.dto.LoginResponse;
import com.paslas.backend.dto.UserResponseDto;
import com.paslas.backend.entity.User;
import com.paslas.backend.mapper.UserMapper;
import com.paslas.backend.security.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final UserService userService;

    @Value("${application.security.cookie.expiration}")
    private int cookieExpiration;

    public LoginResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userService.getByUsername(request.username());

        final String jwt = jwtService.generateToken(user);

        return new LoginResponse(jwt, userMapper.userToUserResponseDto(user));
    }

    public UserResponseDto authUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            //TODO: Throw custom exception
            return null;
        }

        String token = Arrays.stream(cookies)
                .filter(cookie -> "token".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (token == null || token.isEmpty()) {
            //TODO throw custom exception
            return null;
        }
        User user = userService.getByUsername(jwtService.extractSubject(token));
        return userMapper.userToUserResponseDto(user);
    }

    public Cookie createHttpOnlySecureCookieWithToken(String jwt, boolean rememberMe) {
        Cookie cookie = new Cookie("token", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(rememberMe ? cookieExpiration : -1);
        return cookie;
    }

    public Cookie createEmptyCookie() {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }
}
