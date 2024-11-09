package com.aelyashevich.notion.api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record AuthRequestDto(

        @NotNull(message = "Email must be not null.")
        @Email(message = "Invalid email format.")
        String email,

        @NotNull(message = "Password must be not null.")
        @Length(
                min = 8,
                message = "Password must contains min {min} characters."
        )
        String password
) {
}
