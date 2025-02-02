package com.calendar.entities;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "appointment")
public class Appointment implements Serializable {

    @Embeddable
    @Data
    public static class Location{

        private String street;
        private String streetNumber;
        private String postalCode;
        private String city;
        private String country;

        public Location(@Nonnull final String street, @Nonnull final String streetNumber, @Nonnull final String postalCode,
                        @Nonnull final String city, @Nonnull final String country) {
            this.street = street;
            this.streetNumber = streetNumber;
            this.postalCode = postalCode;
            this.city = city;
            this.country = country;
        }

        public Location() {

        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Nonnull
    private String title;
    @Nonnull
    @ManyToOne
    @JoinColumn(name = "author")
    private User author;
    @Nullable
    private String description;
    @Nonnull
    private Timestamp startDateTime;
    @Nonnull
    private Timestamp endDateTime;
    @Embedded
    @Nullable
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Location location;

    public Appointment(@Nonnull final String title, @Nonnull final User author, @Nonnull final Timestamp startDateTime,
                       @Nonnull final Timestamp endDateTime, @Nullable final String description,
                       @Nullable final Location location) {

        this.title = title;
        this.author = author;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.location = location;

    }
}
