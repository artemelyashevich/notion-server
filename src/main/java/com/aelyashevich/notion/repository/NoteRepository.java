package com.aelyashevich.notion.repository;

import com.aelyashevich.notion.entity.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findByUserId(final String userId);
}
