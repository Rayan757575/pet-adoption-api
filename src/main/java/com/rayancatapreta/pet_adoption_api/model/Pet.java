package com.rayancatapreta.pet_adoption_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String species;
    private String breed;
    private Integer age;
    private String description;
    private String photoUrl;
    private String address;
    //private PetStatus petStatus;
    /*@ManyToOne
        private User postedByUser // Who add the pet on the platform
    @CreationTimestamp
    private Instant createdAt;
    */
}
