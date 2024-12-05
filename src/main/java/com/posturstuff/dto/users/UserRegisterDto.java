package com.posturstuff.dto.users;

import com.posturstuff.validation.PasswordMatch;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@PasswordMatch(password = "password", passwordConfirmation = "passwordConfirmation")
public record UserRegisterDto(
        @NotBlank(message = "Provide an username")
        @Size(min = 4, max = 16, message = "The username must have between 4 and 16 characters")
        String username,
        @NotBlank(message = "Provide a display name")
        @Size(min = 4, max = 32, message = "The display name must have between 4 and 32 characters")
        String displayName,
        @NotBlank(message = "Provide an email address")
        @Email(message = "Provide a valid email")
        String email,
        @NotBlank(message = "Provide a password")
        @Size(min = 8, message = "The password must have at least 8 characters")
        String password,
        @NotBlank(message = "Confirm the password")
        @Size(min = 8, message = "The password must have at least 8 characters")
        String passwordConfirmation,
        LocalDate birthDate,
        String profilePicture,
        String profileCover
) {
}
