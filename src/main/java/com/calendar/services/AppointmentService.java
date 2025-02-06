package com.calendar.services;

import com.calendar.communication.in.AppointmentRequest;
import com.calendar.communication.out.AppointmentResponse;
import com.calendar.entities.Appointment;
import com.calendar.entities.User;
import com.calendar.mapper.AppointmentMapper;
import com.calendar.mapper.LocationMapper;
import com.calendar.repositories.IAppointmentRepository;
import com.calendar.repositories.IUserRepository;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {
    private final IAppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final LocationMapper locationMapper;
    private final IUserRepository userRepository;

    public AppointmentService(final IAppointmentRepository appointmentRepository,
                              final AppointmentMapper appointmentMapper,
                              final LocationMapper locationMapper,
                              final IUserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.locationMapper = locationMapper;
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

    public boolean update(final AppointmentRequest appointmentRequest) {

        //Get optionalAppointment from repo
        final Optional<Appointment> optionalAppointment = appointmentRepository.findById(UUID.
                fromString(appointmentRequest.id()));

        // check if present and same author
        if (optionalAppointment.isEmpty() || !appointmentRequest.email().equals(optionalAppointment.get().getAuthor().
                getEmail()))
            return false;

        Appointment appointment = optionalAppointment.get();

        // MapStruct verwenden
        appointment.setLocation(locationMapper.updateLocation(appointmentRequest.location(), appointment.getLocation())); //setzen des location
        appointment = appointmentMapper.updateAppointment(appointmentRequest, appointment); //setzen der restlichen membervariablen von appointment, location wird ignoriert

        appointmentRepository.save(appointment);
        return true;
    }

    @Nullable
    public List<AppointmentResponse> findByEmail(final String email) {

        User user = userRepository.findByEmail(email);

        if (user == null)
            return null;
        return appointmentMapper.toAppointmentResponseList(appointmentRepository.findByAuthor(user));
    }
}
