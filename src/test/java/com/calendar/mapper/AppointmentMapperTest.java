package com.calendar.mapper;

import com.calendar.communication.in.AppointmentRequest;
import com.calendar.communication.out.AppointmentResponse;
import com.calendar.entities.Appointment;
import com.calendar.entities.User;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentMapperTest {

    private final AppointmentMapper appointmentMapper = new AppointmentMapper();

    @Test
    void toAppointment_shouldMapAppointmentRequestToAppointment() {

        final AppointmentRequest appointmentRequest = new AppointmentRequest("b68eddcf-56f7-47f2-ba0c-ea2cfcfbca27",
                "Title", "Max.Power@email.com", Timestamp.valueOf("2014-01-01 00:00:00"),
                Timestamp.valueOf("2014-01-01 00:00:00"), "description");
        final User user = new User("Max", "Mustermann", "Max.Power@email.com", "123456qwe");

        final Appointment appointment = appointmentMapper.toAppointment(appointmentRequest,user);
        assertEquals(appointmentRequest.email() , appointment.getAuthor().getEmail());
        assertEquals(appointmentRequest.startDateTime(), appointment.getStartDateTime());
        assertEquals(appointmentRequest.endDateTime(), appointment.getEndDateTime());
        assertEquals(appointmentRequest.title(), appointment.getTitle());
        assertEquals(appointmentRequest.description(), appointment.getDescription());
    }

    @Test
    void toAppointmentResponse_shouldMapAppointmentToAppointmentResponse() {

        final User user = new User("Max", "Mustermann", "Max.Power@email.com", "123456qwe");
        final Appointment appointment = new Appointment("Title", user, Timestamp.valueOf("2014-01-01 00:00:00"),
                Timestamp.valueOf("2014-01-01 00:00:00"), "description");

        final AppointmentResponse appointmentResponse = appointmentMapper.toAppointmentResponse(appointment);

        assertEquals(appointmentResponse.email() , appointment.getAuthor().getEmail());
        assertEquals(appointmentResponse.startDateTime(), appointment.getStartDateTime());
        assertEquals(appointmentResponse.endDateTime(), appointment.getEndDateTime());
        assertEquals(appointmentResponse.title(), appointment.getTitle());
        assertEquals(appointmentResponse.description(), appointment.getDescription());
    }
}
