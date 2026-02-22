package com.rayancatapreta.pet_adoption_api.dto.user;

public record PasswordUpdateRequestDTO(
        String currentPassword,
        String newPassword
){
}
