package com.calendar.calendar.entities;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Nonnull
    private String title;
    @Nonnull
    @ManyToOne
    @JoinColumn(name="author")
    private User author;
    @Nullable
    private String description;
    @Nonnull
    private Timestamp startDateTime;
    @Nonnull
    private Timestamp endDateTime;

    public Appointment(String title, User author, Timestamp startDateTime, Timestamp endDateTime, String description) {
        this.title = title;
        this.author = author;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
    }
}
