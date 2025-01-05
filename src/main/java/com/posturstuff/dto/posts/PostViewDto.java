package com.posturstuff.dto.posts;

import com.posturstuff.dto.users.UserViewDto;
import com.posturstuff.enums.PostVisibility;

import java.time.LocalDateTime;
import java.util.List;

public record PostViewDto(
        String id,
        String content,
        List<String>images,
        UserViewDto user,
        LocalDateTime createdAt,
        LocalDateTime editedAt,
        PostVisibility visibility
) {
}
