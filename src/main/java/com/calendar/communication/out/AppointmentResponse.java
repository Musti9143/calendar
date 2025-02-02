package com.calendar.communication.out;

import com.calendar.entities.Appointment;

import java.sql.Timestamp;

public record AppointmentResponse(String title, String email, Timestamp startDateTime, Timestamp endDateTime,
                                  String description, Appointment.Location location) { }
