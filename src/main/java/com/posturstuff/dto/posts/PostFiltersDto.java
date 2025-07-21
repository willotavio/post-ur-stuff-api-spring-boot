package com.posturstuff.dto.posts;

import java.time.LocalDate;
import java.util.List;

public record PostFiltersDto(
    String id,
    String userId,
    LocalDate createdAtMin,
    LocalDate createdAtMax,
    LocalDate editedAtMin,
    LocalDate editedAtMax,
    List<Integer> visibility
) {
}
