package com.calendar.controller;

import com.calendar.communication.in.AppointmentRequest;
import com.calendar.services.AppointmentService;
import com.calendar.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final UserService userService;

    public AppointmentController(final AppointmentService appointmentService, final UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAppointment(@RequestBody final AppointmentRequest appointmentRequest) {
        final String email = appointmentRequest.author();
        if(StringUtils.isBlank(email))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required!");

        return appointmentService.create(appointmentRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable final UUID id) {
        appointmentService.deleteById(id);
        return ResponseEntity.ok("Appointment successfully deleted!");
    }

    /*
TODO
    @GetMapping("/findByAuthor/{email}")
    public ResponseEntity<?> findAppointmentsByAuthor(@PathVariable final String email){
        if(StringUtils.isBlank(email))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required!");

        final UserRequest userRequest = userService.findUser(email);
        if(userRequest == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find Appointment, User doesn't exist!");

        List<Appointment> appointments = appointmentService.findByAuthor(userRequest);
        if(appointments != null && !appointments.isEmpty())
            return ResponseEntity.ok(appointments);
        return ResponseEntity.ok("User has no Appointments");
    }
*/
}
