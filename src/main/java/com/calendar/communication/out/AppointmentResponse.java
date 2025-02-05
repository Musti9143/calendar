package com.calendar.communication.out;

import com.calendar.entities.Location;

import java.sql.Timestamp;

public record AppointmentResponse(String id, String title, String email, Timestamp startDateTime, Timestamp endDateTime,
                                  String description, Location location) { }
