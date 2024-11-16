package com.aelyashevich.notion.service.impl;

import com.aelyashevich.notion.entity.User;
import com.aelyashevich.notion.exception.ResourceAlreadyExistsException;
import com.aelyashevich.notion.exception.ResourceNotFoundException;
import com.aelyashevich.notion.repository.UserRepository;
import com.aelyashevich.notion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll(String id) {
        return this.userRepository.findAll();
    }

    @Override
    public User findById(final String id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id '%s' was not found.".formatted(id)));
    }

    @Transactional
    @Override
    public User create(final User entity) {
        if(userRepository.existsByEmail(entity.getEmail())) {
           throw new ResourceAlreadyExistsException("User with email '%s' already exists.".formatted(entity.getEmail()));
        }
        return this.userRepository.save(entity);
    }

    @Transactional
    @Override
    public User update(final String id, final User oldEntity) {
        var candidate = this.findById(id);
        candidate.setEmail(oldEntity.getEmail());
        return this.userRepository.save(candidate);
    }

    @Transactional
    @Override
    public void delete(final String id) {
        var user = this.findById(id);
        this.userRepository.delete(user);
    }

    @Override
    public User findByEmail(final String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email '%s' was not found.".formatted(email)));
    }
}
