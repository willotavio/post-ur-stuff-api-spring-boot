package com.posturstuff.dto.users;

import java.time.LocalDate;

public record UserViewDto(
        String id,
        String username,
        String displayName,
        String email,
        LocalDate joinedAt,
        LocalDate birthDate,
        String accountVisibility,
        String profilePicture,
        String profileCover,
        String description
) {
}
