package com.calendar.calendar.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String titel;
    private User author;
    @OneToMany(mappedBy = "appointment")
    private List<User> invitees;
    private String location;
    private String status;
    private String description;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private Timestamp created_at;

}
