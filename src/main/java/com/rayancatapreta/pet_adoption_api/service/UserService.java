package com.rayancatapreta.pet_adoption_api.service;

import com.rayancatapreta.pet_adoption_api.dto.auth.RegisterRequestDTO;
import com.rayancatapreta.pet_adoption_api.mapper.UserMapper;
import com.rayancatapreta.pet_adoption_api.model.User;
import com.rayancatapreta.pet_adoption_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.existsByEmail(registerRequestDTO.email())) { // Check if the user already exists
            throw new RuntimeException("Email already in use");
        }
        String encodedPassword = passwordEncoder.encode(registerRequestDTO.password()); // Encrypt the password and set
        User user = userMapper.toUser(registerRequestDTO, encodedPassword); // Transform the variable in a User type
        return userRepository.save(user); // Save the user "user"
    }

}
