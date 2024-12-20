package com.calendar.mapper;

import com.calendar.communication.in.AppointmentRequest;
import com.calendar.entities.Appointment;
import com.calendar.entities.User;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public Appointment toAppointment(final AppointmentRequest appointmentRequest, final User user) {
        return new Appointment(appointmentRequest.title(), user, appointmentRequest.startDateTime(), appointmentRequest.endDateTime(), appointmentRequest.description());
    }
}
