package com.rayancatapreta.pet_adoption_api.controller;

import com.rayancatapreta.pet_adoption_api.dto.auth.LoginRequestDTO;
import com.rayancatapreta.pet_adoption_api.dto.auth.TokenResponseDTO;
import com.rayancatapreta.pet_adoption_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        TokenResponseDTO response = authService.login(loginRequestDTO);
        return ResponseEntity.ok(response);
    }
}