package com.aelyashevich.notion.repository;

import com.aelyashevich.notion.entity.Note;
import com.aelyashevich.notion.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findAllByUser(final User user);
}
