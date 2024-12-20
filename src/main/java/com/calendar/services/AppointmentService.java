package com.calendar.services;

import com.calendar.communication.in.AppointmentRequest;
import com.calendar.entities.Appointment;
import com.calendar.entities.User;
import com.calendar.mapper.AppointmentMapper;
import com.calendar.repositories.IAppointmentRepository;
import com.calendar.repositories.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AppointmentService {
    private final IAppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final IUserRepository userRepository;

    public AppointmentService(final IAppointmentRepository appointmentRepository, final AppointmentMapper appointmentMapper, final IUserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> create(final AppointmentRequest appointmentRequest) {
        final User user = userRepository.findByEmail(appointmentRequest.author());
        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot create Appointment, User could not be found!");

        Appointment appointment = appointmentMapper.toAppointment(appointmentRequest, user);
        appointmentRepository.save(appointment);
        return ResponseEntity.ok("Appointment successfully created!");
    }

    public void delete(final Appointment appointment) {
        appointmentRepository.delete(appointment);
    }

    public void deleteById(final UUID id) {
        appointmentRepository.deleteById(id);
    }

    public void update(final Appointment appointment) {
        appointmentRepository.save(appointment);
    }

}
