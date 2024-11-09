package com.aelyashevich.notion.service.impl;

import com.aelyashevich.notion.api.dto.auth.AuthRequestDto;
import com.aelyashevich.notion.entity.User;
import com.aelyashevich.notion.service.AuthService;
import com.aelyashevich.notion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public String login(final AuthRequestDto dto) {
        var user = this.userService.findByEmail(dto.email());
        if (this.passwordEncoder.matches(dto.password(), user.getPassword())) {
            return "login";
        }
        return "forbidden";
    }

    @Override
    public String register(final AuthRequestDto dto) {
        // TODO: Add refresh token
        var user = this.userService.create(
                User.builder()
                        .email(dto.email())
                        .password(this.passwordEncoder.encode(dto.password()))
                        .build()
        );
        return "register";
    }

    @Override
    public Boolean checkIfTokenIsValid(final String accessToken) {
        return null;
    }

    @Override
    public String refreshToken(final String accessToken) {
        return null;
    }
}
