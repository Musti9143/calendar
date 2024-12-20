package com.calendar.communication.out;

import java.sql.Timestamp;

public record AppointmentResponse(String title, String author, Timestamp startDateTime, Timestamp endDateTime, String description) { }
