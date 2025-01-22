package com.posturstuff.dto.users;

import com.posturstuff.validation.PasswordMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@PasswordMatch(password = "newPassword", passwordConfirmation = "newPasswordConfirmation")
public record PasswordUpdateDto(
        @NotBlank(message = "Provide a password")
        @Size(min = 8, message = "The password must have at least 8 characters")
        String newPassword,
        @NotBlank(message = "Provide a password")
        @Size(min = 8, message = "The password must have at least 8 characters")
        String newPasswordConfirmation,
        @NotBlank(message = "Provide a password")
        @Size(min = 8, message = "The password must have at least 8 characters")
        String currentPassword
) {
}
