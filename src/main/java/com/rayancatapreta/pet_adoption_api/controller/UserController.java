package com.rayancatapreta.pet_adoption_api.controller;

import com.rayancatapreta.pet_adoption_api.dto.auth.RegisterRequestDTO;
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
    public ResponseEntity register(@RequestBody @Valid RegisterRequestDTO data) {
        // 1. Verifica se o usuário já existe para evitar duplicidade
        if (this.userRepository.findByEmail(data.email()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // 2. Criptografa a senha antes de criar o objeto User
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        // 3. Instancia o novo usuário com a senha protegida
        User newUser = User.builder()
                .name(data.name())
                .email(data.email())
                .password(encryptedPassword)
                .build();

        // 4. Persistimos no banco de dados
        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}