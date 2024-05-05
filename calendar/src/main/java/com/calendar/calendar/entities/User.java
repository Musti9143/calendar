package com.calendar.calendar.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class User implements Serializable{

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

    @Override
    public String toString(){
        return  "Name = " + getName() +
                ", Surname = " + getSurname() +
                ", Email = " + getEmail();
    }
}
