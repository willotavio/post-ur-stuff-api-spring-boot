package com.posturstuff.dto.posts;

import com.posturstuff.dto.users.UserViewDto;
import com.posturstuff.enums.PostVisibility;

import java.time.LocalDate;
import java.util.List;

public record PostViewDto(
        String id,
        String content,
        List<String>images,
        UserViewDto user,
        LocalDate createdAt,
        LocalDate editedAt,
        PostVisibility visibility
) {
}
