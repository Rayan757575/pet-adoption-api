package com.rayancatapreta.pet_adoption_api.service;

import com.rayancatapreta.pet_adoption_api.dto.user.RegisterRequestDTO;
import com.rayancatapreta.pet_adoption_api.dto.user.UpdateRequestDTO;
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

    public User updateAuthenticatedUser(UpdateRequestDTO updateRequestDTO) {
        User user = getAuthenticatedUser();
        copyNonNullProperties(user, updateRequestDTO);
        return userRepository.save(user);
    }

    public User updateByAdmin(Long id, UpdateRequestDTO updateRequestDTO) {
        User user = findByIdOrThrowBadRequestException(id);
        copyNonNullProperties(user, updateRequestDTO);
        return userRepository.save(user);
    }

    public void deleteAuthenticatedUser() {
        User user = getAuthenticatedUser();
        if (user.isEnabled()) {
            user.setActive(false);
            userRepository.save(user);
        }
    }

    public void reactivateUserById(Long id) {
        User user = findByIdOrThrowBadRequestException(id);
        if (!user.isEnabled()) {
            user.setActive(true);
            userRepository.save(user);
        }
    }

    public void hardDeleteUserByAdmin(Long id) {
        User user = findByIdOrThrowBadRequestException(id);
        userRepository.delete(user);
    }







    private void copyNonNullProperties(User user, UpdateRequestDTO updateRequestDTO) {
        // Checks if the request field is null before updating, to avoid updating values to null and remove the
        // requirement to send all filled fields in a request.
        if (updateRequestDTO.firstName() != null && !updateRequestDTO.firstName().isBlank()){
            user.setFirstName(updateRequestDTO.firstName());
        }
        if (updateRequestDTO.lastName() != null) user.setLastName(updateRequestDTO.lastName());
        if (updateRequestDTO.phone() != null) user.setPhone(updateRequestDTO.phone());
        if (updateRequestDTO.address() != null) user.setAddress(updateRequestDTO.address());
    }
}
