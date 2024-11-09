package com.aelyashevich.notion.api.controller;

import com.aelyashevich.notion.api.dto.OnUpdate;
import com.aelyashevich.notion.api.dto.user.UserDto;
import com.aelyashevich.notion.api.mapper.UserMapper;
import com.aelyashevich.notion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> findAll() {
        var users = this.userService.findAll();
        return this.userMapper.toDto(users);
    }

    @GetMapping("/{id}")
    public UserDto findById(final @PathVariable("id") String userId) {
        var user = this.userService.findById(userId);
        return this.userMapper.toDto(user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto update(
            final @PathVariable("id") String userId,
            final @Validated(OnUpdate.class) @RequestBody UserDto oldUser
    ) {
        var user = this.userService.update(userId, this.userMapper.toEntity(oldUser));
        return this.userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(final @PathVariable("id") String userId) {
        this.userService.delete(userId);
    }
}
