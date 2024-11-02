package com.posturstuff.dto.users;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserRegisterDto(
        @NotBlank(message = "Provide an username")
        String username,
        @NotBlank(message = "Provide a display name")
        String displayName,
        @NotBlank(message = "Provide an email address")
        @Email(message = "Provide a valid email")
        String email,
        @NotBlank(message = "Provide a password")
        String password,
        @NotBlank(message = "Confirm the password")
        String passwordConfirmation,
        LocalDate birthDate,
        String profilePicture,
        String profileCover
) {
}
