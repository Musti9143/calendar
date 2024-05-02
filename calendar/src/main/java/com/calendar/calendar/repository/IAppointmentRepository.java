package com.calendar.calendar.repository;

import com.calendar.calendar.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, UUID>{
}
