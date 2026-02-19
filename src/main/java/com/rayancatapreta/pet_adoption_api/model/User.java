package com.rayancatapreta.pet_adoption_api.model;

import com.rayancatapreta.pet_adoption_api.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users") // prevents problems with reserved words when implementing the database
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Setter(AccessLevel.NONE) // prevents the creation of setters methods do password field
    @Column(nullable = false)
    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean active;

    //useful methods for Spring security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == Role.ROLE_ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }else{
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    //used by spring to identify the user on the system
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
