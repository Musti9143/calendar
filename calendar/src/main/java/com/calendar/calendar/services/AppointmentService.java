package com.calendar.calendar.services;

import com.calendar.calendar.dto.UserDTO;
import com.calendar.calendar.entities.Appointment;
import com.calendar.calendar.entities.User;
import com.calendar.calendar.repositories.IAppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {
    private final IAppointmentRepository appointmentRepository;

    public AppointmentService(final IAppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void create(final Appointment appointment, final UserDTO userDTO) {
        appointmentRepository.save(appointment);
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
