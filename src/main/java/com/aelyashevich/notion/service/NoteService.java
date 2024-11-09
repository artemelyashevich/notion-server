package com.aelyashevich.notion.service;

import com.aelyashevich.notion.entity.Note;
import com.aelyashevich.notion.service.contract.CrudService;

import java.util.List;

public interface NoteService extends CrudService<Note> {

    List<Note> findByUserId(final String userId);
}
