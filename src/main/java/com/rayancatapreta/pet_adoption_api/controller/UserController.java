package com.rayancatapreta.pet_adoption_api.controller;

import com.rayancatapreta.pet_adoption_api.dto.user.*;
import com.rayancatapreta.pet_adoption_api.mapper.UserMapper;
import com.rayancatapreta.pet_adoption_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> listAll(Pageable pageable) {
        return ResponseEntity.ok(userService.listAll(pageable).map(userMapper::toResponseDTO));
    }

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
        userService.register(registerRequestDTO);
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

    @PatchMapping("/{id}/promote")
    public ResponseEntity<Void> promoteToAdmin(@PathVariable Long id, @RequestBody RoleUpdateRequestDTO roleDTO) {
        userService.promoteToAdmin(id, roleDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@RequestBody PasswordUpdateRequestDTO passwordUpdateRequestDTO) {
        userService.updatePassword(passwordUpdateRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<Void> reactivateUserById(@PathVariable Long id) {
        userService.reactivateUserById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe() {
        userService.deleteAuthenticatedUser();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> hardDeleteUserByAdmin(@PathVariable Long id) {
        userService.hardDeleteUserByAdmin(id);
        return ResponseEntity.noContent().build();
    }
}