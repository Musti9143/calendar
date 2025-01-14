package com.calendar.controller;


import com.calendar.communication.in.AppointmentRequest;
import com.calendar.entities.Appointment;
import com.calendar.entities.User;
import com.calendar.services.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentControllerTest {

    @Mock
    AppointmentService appointmentService;

    @Mock
    AppointmentRequest appointmentRequest;

    @InjectMocks
    AppointmentController appointmentController;

    List<Appointment> appointments;
    User user;
    Appointment appointment;

    @BeforeEach
    void setUp() {

        appointments = new ArrayList<>();
        user = new User("Max", "Power", "max.power@email.com");
        appointment = new Appointment("Title", user, Timestamp.valueOf("2014-01-01 00:00:00"),
                Timestamp.valueOf("2014-01-01 00:00:00"), "description");
    }

    @Test
    void createAppointment_shouldReturnOK_whenAppointmentIsCreated() {

        when(appointmentRequest.isValid()).thenReturn(true);
        when(appointmentService.create(appointmentRequest)).thenReturn(true);

        ResponseEntity<String> responseEntity = appointmentController.createAppointment(appointmentRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Appointment successfully created!", responseEntity.getBody());
        verify(appointmentService, times(1)).create(appointmentRequest);
    }

    @Test
    void createAppointment_shouldReturnBadRequest_whenAppointmentRequestIsNotValid() {

        when(appointmentRequest.isValid()).thenReturn(false);

        ResponseEntity<String> responseEntity = appointmentController.createAppointment(appointmentRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Title, Email, Date missing or End Date is before Start Date!", responseEntity.getBody());
        verify(appointmentService, never()).create(appointmentRequest);
    }

    @Test
    void createAppointment_shouldReturnNotFound_whenAuthorDoesNotExist() {

        when(appointmentRequest.isValid()).thenReturn(true);
        when(appointmentService.create(appointmentRequest)).thenReturn(false);

        ResponseEntity<String> responseEntity = appointmentController.createAppointment(appointmentRequest);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Cannot create Appointment, User could not be found!", responseEntity.getBody());
        verify(appointmentService, times(1)).create(appointmentRequest);
    }

    @Test
    void deleteAppointment_shouldReturnOk_whenAppointmentDeleted() {

        when(appointmentService.deleteById(UUID.fromString("b68eddcf-56f7-47f2-ba0c-ea2cfcfbca27"))).
                thenReturn(true);

        ResponseEntity<String> responseEntity = appointmentController.deleteAppointment(
                UUID.fromString("b68eddcf-56f7-47f2-ba0c-ea2cfcfbca27"));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Appointment successfully deleted!", responseEntity.getBody());
        verify(appointmentService, times(1)).deleteById(
                UUID.fromString("b68eddcf-56f7-47f2-ba0c-ea2cfcfbca27"));
    }

    @Test
    void deleteAppointment_shouldReturnNotFound_whenAppointmentDoesNotExist() {

        when(appointmentService.deleteById(UUID.fromString("b68eddcf-56f7-47f2-ba0c-ea2cfcfbca27"))).
                thenReturn(false);

        ResponseEntity<String> responseEntity = appointmentController.deleteAppointment(
                UUID.fromString("b68eddcf-56f7-47f2-ba0c-ea2cfcfbca27"));

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Appointment does not exist!", responseEntity.getBody());
        verify(appointmentService, times(1)).deleteById(
                UUID.fromString("b68eddcf-56f7-47f2-ba0c-ea2cfcfbca27"));
    }

    @Test
    void findAppointmentsByAuthor_shouldReturnListOfAppointments_whenFoundByAuthor() {

        appointments.add(appointment);
        when(appointmentService.findByAuthor(user.getEmail())).thenReturn(appointments);

        List<Appointment> result = appointmentService.findByAuthor(user.getEmail());

        ResponseEntity<?> responseEntity = appointmentController.findAppointmentsByAuthor(
                user.getEmail());

        assertNotNull(result);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(result, responseEntity.getBody());
        verify(appointmentService, times(2)).findByAuthor(user.getEmail());
    }

    @Test
    void findAppointmentsByAuthor_shouldReturnNotFound_whenAuthorNotFound() {

        when(appointmentService.findByAuthor(user.getEmail())).thenReturn(null);

        ResponseEntity<?> responseEntity = appointmentController.findAppointmentsByAuthor(
                user.getEmail());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Cannot find Appointments, because User could not be found!",
                responseEntity.getBody());
        verify(appointmentService, times(1)).findByAuthor(user.getEmail());
    }

    @Test
    void findAppointmentsByAuthor_shouldReturnNotFound_whenAuthorDoesNotHaveAppointments() {

        when(appointmentService.findByAuthor(user.getEmail())).thenReturn(appointments);

        ResponseEntity<?> responseEntity = appointmentController.findAppointmentsByAuthor(
                user.getEmail());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Could not find any Appointments!", responseEntity.getBody());
        verify(appointmentService, times(1)).findByAuthor(user.getEmail());
    }
}
