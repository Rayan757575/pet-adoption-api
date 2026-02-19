package com.rayancatapreta.pet_adoption_api.dto.user;

public record UpdateRequestDTO(
        String firstName,
        String lastName,
        String phone,
        String address
) {
}