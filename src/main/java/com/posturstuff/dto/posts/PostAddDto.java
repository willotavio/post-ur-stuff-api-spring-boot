package com.posturstuff.dto.posts;

import com.posturstuff.enums.PostVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PostAddDto(
        @Size(min = 0, max = 255, message = "The post can have a maximum of 255 characters")
        String content,
        List<String> images,
        @NotBlank(message = "Provide an user ID")
        String userId,
        PostVisibility visibility
) {
}
