package com.calendar.mapper;

import com.calendar.communication.in.AppointmentRequest;
import com.calendar.dto.AppointmentDTO;
import com.calendar.entities.Appointment;
import com.calendar.entities.User;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentDTO toAppointmentDto(final AppointmentRequest request) {
        return new AppointmentDTO(request.title(), request.author(), request.startDateTime(), request.endDateTime(), request.description());
    }

    public AppointmentDTO toAppointmentDto(final Appointment appointment) {
        return new AppointmentDTO(appointment.getTitle(), appointment.getAuthor().getEmail(), appointment.getStartDateTime(), appointment.getEndDateTime(), appointment.getDescription());
    }

    public Appointment toAppointment(final AppointmentDTO appointmentDTO, final User user) {
        return new Appointment(appointmentDTO.title(), user, appointmentDTO.startDateTime(), appointmentDTO.endDateTime(), appointmentDTO.description());
    }
}
