package com.calendar.calendar.controller;

import com.calendar.calendar.entities.Appointment;
import com.calendar.calendar.entities.User;
import com.calendar.calendar.services.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAppointment(@RequestBody Appointment appointment) {
        appointmentService.create(appointment);
        return ResponseEntity.ok("Appointment successfully created!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable UUID id) {
        appointmentService.deleteById(id);
        return ResponseEntity.ok("Appointment successfully deleted!");
    }

    @GetMapping("/findByAuthor")
    public ResponseEntity<List<Appointment>> findAppointmentsByAuthor(@RequestBody User author){
        List<Appointment> appointments = appointmentService.findByAuthor(author);
        if(appointments != null && !appointments.isEmpty())
            return ResponseEntity.ok(appointments);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
