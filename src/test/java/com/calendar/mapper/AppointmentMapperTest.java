package com.calendar.mapper;

import com.calendar.communication.in.AppointmentRequest;
import com.calendar.communication.out.AppointmentResponse;
import com.calendar.entities.Appointment;
import com.calendar.entities.Location;
import com.calendar.entities.User;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentMapperTest {

    private final AppointmentMapper appointmentMapper = new AppointmentMapper();

    @Test
    void toAppointment_shouldMapAppointmentRequestToAppointment() {

        Location location = new Location("Musterstrasse","123",
                "12345","Musterstadt", "DEU");
        final AppointmentRequest appointmentRequest = new AppointmentRequest("b68eddcf-56f7-47f2-ba0c-ea2cfcfbca27",
                "Title", "Max.Power@email.com", Timestamp.valueOf("2014-01-01 00:00:00"),
                Timestamp.valueOf("2014-01-01 00:00:00"), "description", location);
        final User user = new User("Max", "Mustermann", "Max.Power@email.com", "123456qwe");

        final Appointment appointment = appointmentMapper.toAppointment(appointmentRequest,user);

        assertEquals(appointmentRequest.email() , appointment.getAuthor().getEmail());
        assertEquals(appointmentRequest.startDateTime(), appointment.getStartDateTime());
        assertEquals(appointmentRequest.endDateTime(), appointment.getEndDateTime());
        assertEquals(appointmentRequest.title(), appointment.getTitle());
        assertEquals(appointmentRequest.description(), appointment.getDescription());
        assertEquals(appointmentRequest.location(), appointment.getLocation());
    }

    @Test
    void toAppointmentResponse_shouldMapAppointmentToAppointmentResponse() {

        final User user = new User("Max", "Mustermann", "Max.Power@email.com",
                "123456qwe");
        Location location = new Location("Musterstrasse","123",
                "12345","Musterstadt", "DEU");
        final Appointment appointment = new Appointment( "Title", user, Timestamp.valueOf("2014-01-01 00:00:00"),
                Timestamp.valueOf("2014-01-01 00:00:00"), "description", location);
        appointment.setId(UUID.randomUUID());

        final AppointmentResponse appointmentResponse = appointmentMapper.toAppointmentResponse(appointment);
        
        assertEquals(appointmentResponse.email() , appointment.getAuthor().getEmail());
        assertEquals(appointmentResponse.startDateTime(), appointment.getStartDateTime());
        assertEquals(appointmentResponse.endDateTime(), appointment.getEndDateTime());
        assertEquals(appointmentResponse.title(), appointment.getTitle());
        assertEquals(appointmentResponse.description(), appointment.getDescription());
        assertEquals(appointmentResponse.location(), appointment.getLocation());
    }
}
