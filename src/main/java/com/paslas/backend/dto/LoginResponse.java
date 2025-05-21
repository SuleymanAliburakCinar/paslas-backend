package com.paslas.backend.dto;

public record LoginResponse(String token, UserResponseDto user) {
}
