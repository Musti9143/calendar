package com.calendar.mapper;

import com.calendar.communication.in.AppointmentRequest;
import com.calendar.communication.out.AppointmentResponse;
import com.calendar.entities.Appointment;
import com.calendar.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppointmentMapper {

    public Appointment toAppointment(final AppointmentRequest appointmentRequest, final User user) {
        return new Appointment(appointmentRequest.title(), user, appointmentRequest.startDateTime(),
                appointmentRequest.endDateTime(), appointmentRequest.description(), appointmentRequest.location());
    }

    public AppointmentResponse toAppointmentResponse(final Appointment appointment) {
        return new AppointmentResponse(appointment.getId().toString(), appointment.getTitle(), appointment.getAuthor().getEmail(),
                appointment.getStartDateTime(), appointment.getEndDateTime(), appointment.getDescription(), appointment.getLocation());
    }

    public List<AppointmentResponse> toAppointmentResponseList(final List<Appointment> appointmentList) {

        return appointmentList
                .stream()
                .map(this::toAppointmentResponse)
                .toList();
    }
}
