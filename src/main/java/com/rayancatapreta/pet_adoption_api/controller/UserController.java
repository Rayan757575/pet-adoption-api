package com.rayancatapreta.pet_adoption_api.controller;

import com.rayancatapreta.pet_adoption_api.dto.auth.RegisterRequestDTO;
import com.rayancatapreta.pet_adoption_api.enums.Role;
import com.rayancatapreta.pet_adoption_api.model.User;
import com.rayancatapreta.pet_adoption_api.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        // Check if the user already exists
        if (this.userRepository.findByEmail(registerRequestDTO.email()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // Encrypt the password before creating the User object
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequestDTO.password());

        // Creates (Instantiates) the new user
        User newUser = User.builder()
                .name(registerRequestDTO.name())
                .email(registerRequestDTO.email())
                .password(encryptedPassword)
                .role(Role.ROLE_USER)
                .active(true)
                .build();

        // Persists in the database.
        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}