package com.calendar.calendar.controller;

import com.calendar.calendar.communication.in.AppointmentRequest;
import com.calendar.calendar.dto.AppointmentDTO;
import com.calendar.calendar.dto.UserDTO;
import com.calendar.calendar.entities.Appointment;
import com.calendar.calendar.mapper.AppointmentMapper;
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
    private final AppointmentMapper appointmentMapper;

    public AppointmentController(final AppointmentService appointmentService, final UserService userService, final AppointmentMapper appointmentMapper) {
        this.appointmentService = appointmentService;
        this.userService = userService;
        this.appointmentMapper = appointmentMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAppointment(@RequestBody final AppointmentRequest appointmentRequest) {
        final String email = appointmentRequest.author();
        if(StringUtils.isBlank(email))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required!");

        final AppointmentDTO appointmentDTO = appointmentMapper.toAppointmentDto(appointmentRequest);

        return appointmentService.create(appointmentDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable final UUID id) {
        appointmentService.deleteById(id);
        return ResponseEntity.ok("Appointment successfully deleted!");
    }

    @GetMapping("/findByAuthor/{email}")
    public ResponseEntity<?> findAppointmentsByAuthor(@PathVariable final String email){
        if(StringUtils.isBlank(email))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required!");

        final UserDTO userDTO = userService.findUser(email);
        if(userDTO == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find Appointment, User doesn't exist!");

        List<Appointment> appointments = appointmentService.findByAuthor(userDTO);
        if(appointments != null && !appointments.isEmpty())
            return ResponseEntity.ok(appointments);
        return ResponseEntity.ok("User has no Appointments");
    }
}
