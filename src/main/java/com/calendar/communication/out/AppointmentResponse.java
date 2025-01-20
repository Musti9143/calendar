package com.calendar.communication.out;

import java.sql.Timestamp;

public record AppointmentResponse(String title, String email, Timestamp startDateTime, Timestamp endDateTime, String description) { }
