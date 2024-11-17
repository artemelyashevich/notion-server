package com.aelyashevich.notion.api.controller;

import com.aelyashevich.notion.api.dto.OnUpdate;
import com.aelyashevich.notion.api.dto.user.UserDto;
import com.aelyashevich.notion.api.mapper.UserMapper;
import com.aelyashevich.notion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public UserDto findById(final @PathVariable("id") String userId) {
        var user = this.userService.findById(userId);
        return this.userMapper.toDto(user);
    }

    @GetMapping("/current")
    public UserDto findCurrentUser(final @RequestAttribute("id") String id) {
        var user = this.userService.findById(id);
        return this.userMapper.toDto(user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("#id == authentication.principal")
    public UserDto update(
            final @PathVariable("id") String userId,
            final @Validated(OnUpdate.class) @RequestBody UserDto oldUser,
            final @RequestAttribute("id") String id
    ) {
        var user = this.userService.update(userId, this.userMapper.toEntity(oldUser));
        return this.userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("#id == authentication.principal")
    public void delete(final @PathVariable("id") String userId, final @RequestAttribute("id") String id) {
        this.userService.delete(userId);
    }
}
