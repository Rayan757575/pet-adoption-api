package com.rayancatapreta.pet_adoption_api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "The email address cannot be blank")
        @Email
        String email,

        @NotBlank(message = "The password cannot be blank")
        String password
) {
}
