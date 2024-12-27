package com.calendar.repositories;

import com.calendar.entities.Appointment;
import com.calendar.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByAuthor(final User author);

}
