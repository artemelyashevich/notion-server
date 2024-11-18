package com.aelyashevich.notion.api.controller;

import com.aelyashevich.notion.api.dto.note.NoteDto;
import com.aelyashevich.notion.api.mapper.NoteMapper;
import com.aelyashevich.notion.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;
    private final NoteMapper noteMapper;

    @GetMapping
    @PreAuthorize("#id == authentication.principal")
    public List<NoteDto> findAll(final @RequestAttribute("id") String id) {
        var notes = this.noteService.findByUserId(id);
        return this.noteMapper.toDto(notes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal")
    public NoteDto findById(final @PathVariable("id") String noteId, final @RequestAttribute("id") String id) {
        var note = this.noteService.findById(noteId);
        return this.noteMapper.toDto(note);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteDto create(final @Valid @RequestBody NoteDto dto, final @RequestAttribute("id") String id) {
        var note = this.noteService.create(this.noteMapper.toEntity(dto), id);
        return this.noteMapper.toDto(note);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("#id == authentication.principal")
    public NoteDto update(
            final @PathVariable("id") String noteId,
            final @Valid @RequestBody NoteDto dto,
            final @RequestAttribute("id") String id
    ) {
        var note = this.noteService.update(noteId, this.noteMapper.toEntity(dto));
        return this.noteMapper.toDto(note);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("#id == authentication.principal")
    public void delete(final @PathVariable("id") String noteId, final @RequestAttribute("id") String id) {
        this.noteService.delete(noteId);
    }
}
