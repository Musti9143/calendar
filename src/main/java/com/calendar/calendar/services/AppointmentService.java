package com.calendar.calendar.services;

import com.calendar.calendar.dto.AppointmentDTO;
import com.calendar.calendar.dto.UserDTO;
import com.calendar.calendar.entities.Appointment;
import com.calendar.calendar.entities.User;
import com.calendar.calendar.mapper.AppointmentMapper;
import com.calendar.calendar.repositories.IAppointmentRepository;
import com.calendar.calendar.repositories.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public ResponseEntity<String> create(final AppointmentDTO appointmentDTO) {
        final User user = userRepository.findByEmail(appointmentDTO.author());
        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot create Appointment, User could not be found!");

        Appointment appointment = appointmentMapper.toAppointment(appointmentDTO, user);
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

    public List<Appointment> findByAuthor(final UserDTO authorDto) {
        User author = new User();
        return appointmentRepository.findByAuthor(author);
    }

}
