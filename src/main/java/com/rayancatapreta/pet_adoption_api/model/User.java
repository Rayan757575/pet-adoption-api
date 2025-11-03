package com.rayancatapreta.pet_adoption_api.model;

import com.rayancatapreta.pet_adoption_api.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@Table(name = "users") //avoids problems with reserved words when implementing the database
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    private String lastName;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // --- Interface fields UserDetails ---

    // Define account status (necessary for Spring Security)
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;
}
