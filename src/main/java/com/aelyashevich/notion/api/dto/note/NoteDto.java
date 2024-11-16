package com.aelyashevich.notion.api.dto.note;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record NoteDto(
        String id,

        @NotNull(message = "Title must be not nell.")
        String title,

        @NotNull(message = "Content must be not nell.")
        String content,

        String userId,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
