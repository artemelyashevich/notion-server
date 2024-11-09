package com.aelyashevich.notion.api.controller;

import com.aelyashevich.notion.api.dto.note.NoteDto;
import com.aelyashevich.notion.api.mapper.NoteMapper;
import com.aelyashevich.notion.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;
    private final NoteMapper noteMapper;

    @GetMapping
    public List<NoteDto> findAll() {
        var notes = this.noteService.findAll();
        return this.noteMapper.toDto(notes);
    }

    @GetMapping("/{id}")
    public NoteDto findById(final @PathVariable("id") String noteId) {
        var note = this.noteService.findById(noteId);
        return this.noteMapper.toDto(note);
    }

    @GetMapping("/user/{id}")
    public List<NoteDto> findByUserId(final @PathVariable("id") String userId) {
        var notes = this.noteService.findByUserId(userId);
        return this.noteMapper.toDto(notes);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteDto create(final @Valid @RequestBody NoteDto dto) {
        var note = this.noteService.create(this.noteMapper.toEntity(dto));
        return this.noteMapper.toDto(note);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public NoteDto update(final @PathVariable("id") String noteId, final @Valid @RequestBody NoteDto dto) {
        var note = this.noteService.update(noteId, this.noteMapper.toEntity(dto));
        return this.noteMapper.toDto(note);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(final @PathVariable("id") String noteId) {
        this.noteService.delete(noteId);
    }
}
