package com.aelyashevich.notion.service;

import com.aelyashevich.notion.api.dto.auth.AuthRequestDto;

public interface AuthService {

    String login(final AuthRequestDto dto);

    String register(final AuthRequestDto dto);

    Boolean checkIfTokenIsValid(final String accessToken);

    String refreshToken(final String accessToken);
}
