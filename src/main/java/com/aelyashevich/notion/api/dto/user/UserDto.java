package com.aelyashevich.notion.api.dto.user;

import com.aelyashevich.notion.api.dto.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

        private String id;

        private String email;

        @NotNull(message = "Password must be not null.", groups = OnUpdate.class)
        @Length(
                min = 8,
                message = "Password must contains min {min} characters.",
                groups = OnUpdate.class
        )
        private String password;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

        @Override
        public String toString() {
                final StringBuilder sb = new StringBuilder("UserDto{");
                sb.append("id='").append(id).append('\'');
                sb.append(", email='").append(email).append('\'');
                sb.append(", password='").append(password).append('\'');
                sb.append(", createdAt=").append(createdAt);
                sb.append(", updatedAt=").append(updatedAt);
                sb.append('}');
                return sb.toString();
        }
}
