package com.aelyashevich.notion.api.controller;

import com.aelyashevich.notion.api.dto.auth.AuthRequestDto;
import com.aelyashevich.notion.api.dto.auth.AuthResponseDto;
import com.aelyashevich.notion.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponseDto login(@Valid @RequestBody AuthRequestDto dto) {
        var token = this.authService.login(dto);
        return new AuthResponseDto(token);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponseDto register(@Valid @RequestBody AuthRequestDto dto) {
        var token = this.authService.register(dto);
        return new AuthResponseDto(token);
    }

    @GetMapping("/token/{token}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Boolean checkIfTokenIsValid(final @PathVariable("token") String token) {
        return this.authService.checkIfTokenIsValid(token);
    }
}
