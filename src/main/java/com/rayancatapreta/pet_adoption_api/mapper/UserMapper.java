package com.rayancatapreta.pet_adoption_api.mapper;

import com.rayancatapreta.pet_adoption_api.dto.user.RegisterRequestDTO;
import com.rayancatapreta.pet_adoption_api.dto.user.UpdateRequestDTO;
import com.rayancatapreta.pet_adoption_api.dto.user.UserResponseDTO;
import com.rayancatapreta.pet_adoption_api.model.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
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

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "email", ignore = true)
    public abstract void updateUserFromDto(UpdateRequestDTO updateRequestDTO, @MappingTarget User user);

    @AfterMapping
    protected void validateFirstName(@MappingTarget User user) {
        if (user.getFirstName() != null && user.getFirstName().isBlank()) {
            // Aqui você pode decidir se lança erro ou mantém o anterior.
            // Como o MapStruct já rodou, se quiser manter o anterior em caso de blank,
            // a lógica manual ainda é mais precisa, mas o null check o MapStruct já resolve.
            System.out.println("Aviso: Tentativa de atualizar firstName com valor em branco ignorada.");
        }
    }
}