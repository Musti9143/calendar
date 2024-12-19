package com.calendar.dto;

import java.sql.Timestamp;

public record AppointmentDTO(String title, String author, Timestamp startDateTime, Timestamp endDateTime, String description) { }
