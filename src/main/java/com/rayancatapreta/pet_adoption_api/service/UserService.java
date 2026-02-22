package com.rayancatapreta.pet_adoption_api.service;

import com.rayancatapreta.pet_adoption_api.dto.user.PasswordUpdateRequestDTO;
import com.rayancatapreta.pet_adoption_api.dto.user.RegisterRequestDTO;
import com.rayancatapreta.pet_adoption_api.dto.user.RoleUpdateRequestDTO;
import com.rayancatapreta.pet_adoption_api.dto.user.UpdateRequestDTO;
import com.rayancatapreta.pet_adoption_api.mapper.UserMapper;
import com.rayancatapreta.pet_adoption_api.model.User;
import com.rayancatapreta.pet_adoption_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // GETS METHODS
    public Page<User> listAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findByIdOrThrowBadRequestException(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not Found"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with the email address provided"));
    }

    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // POST METHODS
    public User register(RegisterRequestDTO registerRequestDTO) {
        // Check if the user already exists to avoid duplicates
        if (this.userRepository.existsByEmail(registerRequestDTO.email())) {
            throw new RuntimeException("Email already in use");
        }
        String encodedPassword = passwordEncoder.encode(registerRequestDTO.password()); // Encrypt the password
        // Transform the variables "registerRequestDTO" and "encodedPassword" in a User type to be saved
        User user = userMapper.toUser(registerRequestDTO, encodedPassword);
        return userRepository.save(user);
    }

    // PATCH/PUT METHODS
    public User updateAuthenticatedUser(UpdateRequestDTO updateRequestDTO) {
        User user = getAuthenticatedUser();
        userMapper.updateUserFromDto(updateRequestDTO, user); // O Mapper faz o trabalho sujo
        return userRepository.save(user);
    }

    public User updateByAdmin(Long id, UpdateRequestDTO updateRequestDTO) {
        User user = findByIdOrThrowBadRequestException(id);
        userMapper.updateUserFromDto(updateRequestDTO, user);
        return userRepository.save(user);
    }

    public void updatePassword(PasswordUpdateRequestDTO passwordUpdateRequestDTO) {
        User user = getAuthenticatedUser();
        if (!passwordEncoder.matches(passwordUpdateRequestDTO.currentPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect current password");
        }
        user.updatePassword(passwordEncoder.encode(passwordUpdateRequestDTO.newPassword()));
        userRepository.save(user);
    }

    public void promoteToAdmin(Long id, RoleUpdateRequestDTO roleDTO) {
        User user = findByIdOrThrowBadRequestException(id);
        if (user.getRole() == roleDTO.role()) {
            throw new RuntimeException("The user already holds the " + roleDTO.role() + " role");
        }
        user.setRole(roleDTO.role());
        userRepository.save(user);
    }

    // DELETE METHODS
    public void deleteAuthenticatedUser() {
        User user = getAuthenticatedUser();
        if (!user.isEnabled()) {
            throw new RuntimeException("This account has already been deactivated");
        }
        user.setActive(false);
        userRepository.save(user);
    }

    public void reactivateUserById(Long id) {
        User user = findByIdOrThrowBadRequestException(id);
        if (user.isEnabled()) {
            throw new RuntimeException("This user is already active in the system");
        }
        user.setActive(true);
        userRepository.save(user);
    }

    public void hardDeleteUserByAdmin(Long id) {
        User user = findByIdOrThrowBadRequestException(id);
        userRepository.delete(user);
    }

}
