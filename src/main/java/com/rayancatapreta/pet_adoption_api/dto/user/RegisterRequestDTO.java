package com.rayancatapreta.pet_adoption_api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank(message = "The user name address cannot be blank")
        String firstName,

        @NotBlank(message = "The email address cannot be blank")
        @Email
        String email,

        @NotBlank(message = "The password cannot be blank")
        @Size(min = 6, message = "The password must be longer than 6 characters") //define the minimum password's size
        String password
) {
}