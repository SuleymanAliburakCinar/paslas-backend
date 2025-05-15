package com.paslas.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDto {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @Email
    private String email;

    @NotBlank
    @Size(min = 8, message = "Parola en az 8 karakterden oluşmalıdır.")
    private String password;

    @NotBlank
    private String confirmPassword;
}
