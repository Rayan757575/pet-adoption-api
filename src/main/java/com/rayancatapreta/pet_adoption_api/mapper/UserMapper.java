package com.rayancatapreta.pet_adoption_api.mapper;

import com.rayancatapreta.pet_adoption_api.dto.user.RegisterRequestDTO;
import com.rayancatapreta.pet_adoption_api.dto.user.UserResponseDTO;
import com.rayancatapreta.pet_adoption_api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    //public static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", source = "encodedPassword") // Ignore to be treated in the Service file
    @Mapping(target = "role", constant = "ROLE_USER") // Define a default role
    @Mapping(target = "active", constant = "true") // Define as active by default
    @Mapping(target = "id", ignore = true) // Model fields that aren't in the DTO
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "address", ignore = true)
    public abstract User toUser(RegisterRequestDTO registerRequestDTO, String encodedPassword);

    public abstract UserResponseDTO toResponseDTO(User user);
}