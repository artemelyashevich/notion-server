package com.aelyashevich.notion.service;

import com.aelyashevich.notion.entity.Note;
import com.aelyashevich.notion.service.contract.CrudService;

import java.util.List;

public interface NoteService {

    List<Note> findByUserId(final String userId);

    Note findById(final String id);

    Note create(final Note entity, String id);

    Note update(final String id, final Note oldEntity);

    void delete(final String id);
}
