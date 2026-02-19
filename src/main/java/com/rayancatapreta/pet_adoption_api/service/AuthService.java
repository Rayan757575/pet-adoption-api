package com.rayancatapreta.pet_adoption_api.service;

import com.rayancatapreta.pet_adoption_api.dto.auth.LoginRequestDTO;
import com.rayancatapreta.pet_adoption_api.dto.auth.TokenResponseDTO;
import com.rayancatapreta.pet_adoption_api.model.User;
import com.rayancatapreta.pet_adoption_api.infra.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.email(),
                        loginRequestDTO.password()
        );

        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        String token = this.tokenService.generateToken((User) auth.getPrincipal());

        return new TokenResponseDTO(token);
    }
}