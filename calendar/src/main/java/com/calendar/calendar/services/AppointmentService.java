package com.calendar.calendar.services;

import com.calendar.calendar.entities.Appointment;
import com.calendar.calendar.entities.User;
import com.calendar.calendar.repositories.IAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;

@Service
public class AppointmentService {

    private final IAppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(IAppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment createAppointment(String title, User author, String description, Timestamp startDateTime, Timestamp endDateTime) {
        Appointment appointment = new Appointment(null, title, author, description, startDateTime, endDateTime);
        return appointmentRepository.save(appointment);
    }
}
