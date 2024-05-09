package com.calendar.calendar.mapper;

import com.calendar.calendar.communication.in.AppointmentRequestV1;
import com.calendar.calendar.dto.AppointmentDTO;
import com.calendar.calendar.dto.UserDTO;
import com.calendar.calendar.entities.Appointment;
import com.calendar.calendar.entities.User;

public class AppointmentMapper {
    private final UserMapper userMapper;

    public AppointmentMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public AppointmentDTO toAppointmentDto(AppointmentRequestV1 request) {
        return new AppointmentDTO(request.title(), request.author(), request.startDateTime(), request.endDateTime(), request.description());
    }

    public AppointmentDTO toAppointmentDto(Appointment appointment) {
        return new AppointmentDTO(appointment.getTitle(), appointment.getAuthor().getEmail(), appointment.getStartDateTime(), appointment.getEndDateTime(), appointment.getDescription());
    }

    public Appointment toAppointment(AppointmentDTO appointmentDTO, UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        return new Appointment(appointmentDTO.title(), user, appointmentDTO.startDateTime(), appointmentDTO.endDateTime(), appointmentDTO.description());
    }
}
