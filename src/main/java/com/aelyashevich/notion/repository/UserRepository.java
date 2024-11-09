package com.aelyashevich.notion.repository;

import com.aelyashevich.notion.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(final String email);

    Boolean existsByEmail(final String email);
}
