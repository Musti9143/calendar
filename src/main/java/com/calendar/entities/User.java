package com.calendar.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "user")
public class User implements Serializable {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Nonnull
    private String name;
    @Nonnull
    private String surname;
    @Nonnull
    private String email;
    @Nonnull
    private String password;

    public User(@Nonnull final String name, @Nonnull final String surname, @Nonnull final String email, @Nonnull final String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
}
