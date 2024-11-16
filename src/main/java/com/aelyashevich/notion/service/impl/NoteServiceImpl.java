package com.aelyashevich.notion.service.impl;

import com.aelyashevich.notion.entity.Note;
import com.aelyashevich.notion.exception.ResourceNotFoundException;
import com.aelyashevich.notion.repository.NoteRepository;
import com.aelyashevich.notion.service.NoteService;
import com.aelyashevich.notion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserService userService;

    @Override
    public Note findById(final String id) {
        return this.noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note with id '%s' was not found".formatted(id)));
    }

    @Transactional
    @Override
    public Note create(final Note entity, final String id) {
        entity.setUser(this.userService.findById(id));
        return this.noteRepository.save(entity);
    }

    @Transactional
    @Override
    public Note update(final String id, final Note oldEntity) {
        var note = this.findById(id);
        note.setTitle(oldEntity.getTitle());
        note.setContent(oldEntity.getContent());
        return this.noteRepository.save(note);
    }

    @Transactional
    @Override
    public void delete(final String id) {
        var note = this.findById(id);
        this.noteRepository.delete(note);
    }

    @Override
    public List<Note> findByUserId(final String userId) {
        return this.noteRepository.findAllByUser(this.userService.findById(userId));
    }
}
