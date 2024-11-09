package com.aelyashevich.notion.api.mapper.impl;

import com.aelyashevich.notion.api.dto.note.NoteDto;
import com.aelyashevich.notion.api.mapper.NoteMapper;
import com.aelyashevich.notion.entity.Note;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteMapperImpl implements NoteMapper {

    @Override
    public Note toEntity(final NoteDto dto) {
        return Note.builder()
                .title(dto.title())
                .content(dto.content())
                .userId(dto.userId())
                .createdAt(dto.createdAt())
                .updatedAt(dto.updatedAt())
                .build();
    }

    @Override
    public NoteDto toDto(final Note entity) {
        return new NoteDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getUserId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    @Override
    public List<NoteDto> toDto(final List<Note> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
}
