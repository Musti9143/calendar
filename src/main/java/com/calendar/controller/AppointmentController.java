package com.calendar.controller;

import com.calendar.communication.in.AppointmentRequest;
import com.calendar.entities.Appointment;
import com.calendar.services.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(final AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAppointment(@RequestBody final AppointmentRequest appointmentRequest) {

        if (!appointmentRequest.isValid())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title, Email, Date missing or End Date is " +
                    "before Start Date!");

        if (appointmentService.create(appointmentRequest))
            return ResponseEntity.ok("Appointment successfully created!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot create Appointment, User could not be found!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable final UUID id) {

        if (appointmentService.deleteById(id))
            return ResponseEntity.ok("Appointment successfully deleted!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment does not exist!");
    }

    @GetMapping("/findByAuthor/{email}")
    public ResponseEntity<?> findAppointmentsByAuthor(@PathVariable final String email) {

        List<Appointment> appointments = appointmentService.findByEmail(email);

        if (appointments == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find Appointments, because User could " +
                    "not be found!");
        else if (appointments.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find any Appointments!");
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAppointment(@RequestBody final AppointmentRequest appointmentRequest) {

        if (!appointmentRequest.isValid(appointmentRequest.id()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title, Email, Date or ID is missing or " +
                    "End Date is before Start Date!");

        if (appointmentService.update(appointmentRequest))
            return ResponseEntity.ok("Appointment successfully updated!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Appointments found for the given Author!");
    }
}
