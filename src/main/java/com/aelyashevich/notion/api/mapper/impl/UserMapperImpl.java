package com.aelyashevich.notion.api.mapper.impl;

import com.aelyashevich.notion.api.dto.user.UserDto;
import com.aelyashevich.notion.api.mapper.UserMapper;
import com.aelyashevich.notion.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(final UserDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    @Override
    public UserDto toDto(final User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    @Override
    public List<UserDto> toDto(final List<User> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
}
