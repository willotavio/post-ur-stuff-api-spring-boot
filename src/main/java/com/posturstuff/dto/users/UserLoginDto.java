package com.posturstuff.dto.users;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(
        @NotBlank(message = "Provide an username")
        String username,
        @NotBlank(message = "Provide a password")
        String password
) {
}
