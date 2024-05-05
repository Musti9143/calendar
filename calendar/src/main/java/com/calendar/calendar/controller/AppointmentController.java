package com.calendar.calendar.controller;

import com.calendar.calendar.entities.Appointment;
import com.calendar.calendar.entities.User;
import com.calendar.calendar.services.AppointmentService;
import com.calendar.calendar.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final UserService userService;

    public AppointmentController(AppointmentService appointmentService, UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAppointment(@RequestBody Appointment appointment) {
        String email = appointment.getAuthor().getEmail();
        if(StringUtils.isBlank(email))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required!");

        User user = userService.findUser(email);
        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot create Appointment, User could not be found!");

        appointment.setAuthor(user);
        appointmentService.create(appointment);
        return ResponseEntity.ok("Appointment successfully created!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable UUID id) {
        appointmentService.deleteById(id);
        return ResponseEntity.ok("Appointment successfully deleted!");
    }

    @GetMapping("/findByAuthor/{email}")
    public ResponseEntity<?> findAppointmentsByAuthor(@PathVariable String email){
        if(StringUtils.isBlank(email))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required!");

        User user = userService.findUser(email);
        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find Appointment, User doesn't exist!");

        List<Appointment> appointments = appointmentService.findByAuthor(user);
        if(appointments != null && !appointments.isEmpty())
            return ResponseEntity.ok(appointments);
        return ResponseEntity.ok("User has no Appointments");
    }

}
