package com.rayancatapreta.pet_adoption_api.service;

import com.rayancatapreta.pet_adoption_api.dto.user.RegisterRequestDTO;
import com.rayancatapreta.pet_adoption_api.mapper.UserMapper;
import com.rayancatapreta.pet_adoption_api.model.User;
import com.rayancatapreta.pet_adoption_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User findByIdOrThrowBadRequestException(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not Found"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with the email address provided"));
    }

    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User register(RegisterRequestDTO registerRequestDTO) {
        // Check if the user already exists to avoid duplicates
        if (this.userRepository.existsByEmail(registerRequestDTO.email())) {
            throw new RuntimeException("Email already in use");
        }
        String encodedPassword = this.passwordEncoder.encode(registerRequestDTO.password()); // Encrypt the password
        // Transform the variables "registerRequestDTO" and "encodedPassword" in a User type to be saved
        User user = this.userMapper.toUser(registerRequestDTO, encodedPassword);
        return this.userRepository.save(user);
    }
}
