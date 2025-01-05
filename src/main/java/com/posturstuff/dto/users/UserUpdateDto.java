package com.posturstuff.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserUpdateDto(
        @Size(min = 4, max = 16, message = "The username must have between 4 and 16 characters")
        String username,
        @Size(min = 4, max = 32, message = "The display name must have between 4 and 32 characters")
        String displayName,
        @Email(message = "Provide a valid email")
        String email,
        @Size(min = 8, message = "The password must have at least 8 characters")
        String password,
        @Size(min = 8, message = "The password must have at least 8 characters")
        String passwordConfirmation,
        LocalDate birthDate,
        int accountVisibility,
        String profilePicture,
        String profileCover,
        @Size(max = 200, message = "The description must have a maximum of 200 characters")
        String description
) {
}
