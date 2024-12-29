package com.calendar.services;

import com.calendar.communication.in.AppointmentRequest;
import com.calendar.entities.Appointment;
import com.calendar.entities.User;
import com.calendar.mapper.AppointmentMapper;
import com.calendar.repositories.IAppointmentRepository;
import com.calendar.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {
    private final IAppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final IUserRepository userRepository;

    public AppointmentService(final IAppointmentRepository appointmentRepository, final AppointmentMapper appointmentMapper,
                              final IUserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.userRepository = userRepository;
    }

    public boolean create(final AppointmentRequest appointmentRequest) {

        final User user = userRepository.findByEmail(appointmentRequest.email());
        if (user == null)
            return false;

        Appointment appointment = appointmentMapper.toAppointment(appointmentRequest, user);
        appointmentRepository.save(appointment);
        return true;
    }

    public boolean deleteById(final UUID id) {

        final Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (appointment.isEmpty())
            return false;
        appointmentRepository.deleteById(id);
        return true;
    }

    public void update(final Appointment appointment) {

        appointmentRepository.save(appointment);
    }

    public List<Appointment> findByAuthor(final String email) {

        User user = userRepository.findByEmail(email);

        if (user == null)
            return null;
        return appointmentRepository.findByAuthor(user);
    }
}
