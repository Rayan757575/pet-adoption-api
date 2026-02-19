package com.rayancatapreta.pet_adoption_api.infra.exception;

import org.springframework.http.HttpStatus;

public record ErrorMessageDTO(
        HttpStatus status,
        String message
) {
}