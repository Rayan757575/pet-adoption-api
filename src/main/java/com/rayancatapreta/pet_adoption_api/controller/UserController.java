package com.rayancatapreta.pet_adoption_api.controller;

import com.rayancatapreta.pet_adoption_api.dto.user.RegisterRequestDTO;
import com.rayancatapreta.pet_adoption_api.dto.user.UpdateRequestDTO;
import com.rayancatapreta.pet_adoption_api.dto.user.UserResponseDTO;
import com.rayancatapreta.pet_adoption_api.mapper.UserMapper;
import com.rayancatapreta.pet_adoption_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.toResponseDTO(userService.findByIdOrThrowBadRequestException(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<UserResponseDTO> findByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userMapper.toResponseDTO(userService.findByEmail(email)));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMe() {
        return ResponseEntity.ok(userMapper.toResponseDTO(userService.getAuthenticatedUser()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        this.userService.register(registerRequestDTO); // Delegate the logic to register a new user
        return ResponseEntity.ok().build();
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDTO> updateMe(@RequestBody UpdateRequestDTO updateRequestDTO) {
        return ResponseEntity.ok(userMapper.toResponseDTO(userService.updateAuthenticatedUser(updateRequestDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateByAdmin(@PathVariable Long id, @RequestBody UpdateRequestDTO updateRequestDTO) {
        return ResponseEntity.ok(userMapper.toResponseDTO(userService.updateByAdmin(id, updateRequestDTO)));
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<Void> reactivateUserById(@PathVariable Long id) {
        userService.reactivateUserById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe() {
        this.userService.deleteAuthenticatedUser(); // Delegate the logic for the soft deletion of a user
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> hardDeleteUserByAdmin(@PathVariable Long id) {
        userService.hardDeleteUserByAdmin(id);
        return ResponseEntity.noContent().build();
    }
}