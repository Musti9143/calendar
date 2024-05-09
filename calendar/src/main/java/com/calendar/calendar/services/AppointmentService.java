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

    public AppointmentService(IAppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void create(Appointment appointment, UserDTO userDTO) {
        appointmentRepository.save(appointment);
    }

    public void delete(Appointment appointment) {
        appointmentRepository.delete(appointment);
    }

    public void deleteById(UUID id) {
        appointmentRepository.deleteById(id);
    }

    public void update(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public List<Appointment> findByAuthor(UserDTO authorDto) {
        User author = new User();
        return appointmentRepository.findByAuthor(author);
    }

}
