package com.rayancatapreta.pet_adoption_api.dto.user;

import com.rayancatapreta.pet_adoption_api.enums.Role;

public record UserResponseDTO(
        Long id,
        String email,
        String firstName,
        String lastName,
        String phone,
        String address,
        Role role
) {
}