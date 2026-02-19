package com.rayancatapreta.pet_adoption_api.controller;

import com.rayancatapreta.pet_adoption_api.dto.user.RegisterRequestDTO;
import com.rayancatapreta.pet_adoption_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        this.userService.register(registerRequestDTO); // Delegate the logic to register a new user
        return ResponseEntity.ok().build();
    }
}