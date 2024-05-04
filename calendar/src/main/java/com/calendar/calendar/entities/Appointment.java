package com.calendar.calendar.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "appointment")
public class Appointment implements Serializable {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Nonnull
    private String title;
    @Nonnull
    private User author;
    @Nullable
    private String description;
    @Nonnull
    private Timestamp startDateTime;
    @Nonnull
    private Timestamp endDateTime;
}
