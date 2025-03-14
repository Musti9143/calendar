package com.calendar.controller;


import com.calendar.communication.in.AppointmentRequest;
import com.calendar.communication.out.AppointmentResponse;
import com.calendar.communication.out.ErrorResponse;
import com.calendar.communication.out.GenericResponse;
import com.calendar.entities.Appointment;
import com.calendar.entities.Location;
import com.calendar.entities.User;
import com.calendar.mapper.AppointmentMapper;
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
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRequest appointmentRequest;

    @Mock
    private AppointmentResponse appointmentResponse;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentController appointmentController;

    private List<AppointmentResponse> appointments;
    private User user;
    private Appointment appointment;
    private Location location;


    @BeforeEach
    void setUp() {

        location = new Location("Musterstrasse","123","12345", "Musterstadt", "DEU");
        appointments = new ArrayList<>();
        user = new User("Max", "Power", "max.power@email.com", "123456qwe");
        appointment = new Appointment("Title", user, Timestamp.valueOf("2014-01-01 00:00:00"),
                Timestamp.valueOf("2014-01-01 00:00:00"), "description", location);
        appointmentResponse = appointmentMapper.toAppointmentResponse(appointment);
    }

    @Test
    void createAppointment_shouldReturnOK_whenAppointmentIsCreated() {

        when(appointmentRequest.isAllValid()).thenReturn(true);
        when(appointmentService.create(appointmentRequest)).thenReturn(true);

        ResponseEntity<GenericResponse<String>> responseEntity = appointmentController.
                createAppointment(appointmentRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(GenericResponse.success("Appointment successfully created!"),
                responseEntity.getBody());
        verify(appointmentService, times(1)).create(appointmentRequest);
    }

    @Test
    void createAppointment_shouldReturnBadRequest_whenAppointmentRequestIsNotValid() {

        when(appointmentRequest.isAllValid()).thenReturn(false);

        ResponseEntity<GenericResponse<String>> responseEntity = appointmentController.
                createAppointment(appointmentRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(GenericResponse.error(new ErrorResponse("Title, Email, Date missing or End Date is before Start Date!")),
                responseEntity.getBody());
        verify(appointmentService, never()).create(appointmentRequest);
    }

    @Test
    void createAppointment_shouldReturnNotFound_whenAuthorDoesNotExist() {

        when(appointmentRequest.isAllValid()).thenReturn(true);
        when(appointmentService.create(appointmentRequest)).thenReturn(false);

        ResponseEntity<GenericResponse<String>> responseEntity = appointmentController.
                createAppointment(appointmentRequest);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(GenericResponse.error(new ErrorResponse("Cannot create Appointment, User could not be found!")),
                responseEntity.getBody());
        verify(appointmentService, times(1)).create(appointmentRequest);
    }

    @Test
    void deleteAppointment_shouldReturnOk_whenAppointmentDeleted() {

        when(appointmentService.deleteById(UUID.fromString("b68eddcf-56f7-47f2-ba0c-ea2cfcfbca27"))).
                thenReturn(true);

        ResponseEntity<GenericResponse<String>> responseEntity = appointmentController.
                deleteAppointment(UUID.fromString("b68eddcf-56f7-47f2-ba0c-ea2cfcfbca27"));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(GenericResponse.success("Appointment successfully deleted!"),
                responseEntity.getBody());
        verify(appointmentService, times(1)).deleteById(
                UUID.fromString("b68eddcf-56f7-47f2-ba0c-ea2cfcfbca27"));
    }

    @Test
    void deleteAppointment_shouldReturnNotFound_whenAppointmentDoesNotExist() {

        when(appointmentService.deleteById(UUID.fromString("b68eddcf-56f7-47f2-ba0c-ea2cfcfbca27"))).
                thenReturn(false);

        ResponseEntity<GenericResponse<String>> responseEntity = appointmentController.
                deleteAppointment(UUID.fromString("b68eddcf-56f7-47f2-ba0c-ea2cfcfbca27"));

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(GenericResponse.error(new ErrorResponse("Appointment does not exist!")),
                responseEntity.getBody());
        verify(appointmentService, times(1)).deleteById(
                UUID.fromString("b68eddcf-56f7-47f2-ba0c-ea2cfcfbca27"));
    }

    @Test
    void findAppointmentsByAuthor_shouldReturnListOfAppointments_whenAppointmentOfAuthorFound() {

        appointments.add(appointmentResponse);
        when(appointmentService.findByEmail(user.getEmail())).thenReturn(appointments);

        List<AppointmentResponse> result = appointmentService.findByEmail(user.getEmail());

        ResponseEntity<GenericResponse<List<AppointmentResponse>>> responseEntity = appointmentController.
                findAppointmentsByAuthor(user.getEmail());

        assertNotNull(result);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(GenericResponse.success(result), responseEntity.getBody());
        verify(appointmentService, times(2)).findByEmail(user.getEmail());
    }

    @Test
    void findAppointmentsByAuthor_shouldReturnNotFound_whenAuthorNotFound() {

        when(appointmentService.findByEmail(user.getEmail())).thenReturn(null);

        ResponseEntity<GenericResponse<List<AppointmentResponse>>> responseEntity = appointmentController.
                findAppointmentsByAuthor(user.getEmail());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(GenericResponse.error(new ErrorResponse("Cannot find Appointments, because User could not be found!")),
                responseEntity.getBody());
        verify(appointmentService, times(1)).findByEmail(user.getEmail());
    }

    @Test
    void findAppointmentsByAuthor_shouldReturnNotFound_whenAuthorDoesNotHaveAppointments() {

        when(appointmentService.findByEmail(user.getEmail())).thenReturn(appointments);

        ResponseEntity<GenericResponse<List<AppointmentResponse>>> responseEntity = appointmentController.
                findAppointmentsByAuthor(user.getEmail());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(GenericResponse.error(new ErrorResponse("Could not find any Appointments!")), responseEntity.getBody());
        verify(appointmentService, times(1)).findByEmail(user.getEmail());
    }

    @Test
    void updateAppointment_shouldReturnOk_whenAppointmentIsUpdated() {

        when(appointmentRequest.isValid(appointmentRequest.id())).thenReturn(true);
        when(appointmentService.update(appointmentRequest)).thenReturn(true);

        ResponseEntity<GenericResponse<String>> responseEntity = appointmentController.updateAppointment(appointmentRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(GenericResponse.success("Appointment successfully updated!"),
                responseEntity.getBody());
        verify(appointmentRequest, times(2)).isValid(appointmentRequest.id());
        verify(appointmentService, times(1)).update(appointmentRequest);
    }

    @Test
    void updateAppointment_shouldReturnBadRequest_whenAppointmentRequestIsInvalid() {

        when(appointmentRequest.isValid(appointmentRequest.id())).thenReturn(false);

        ResponseEntity<GenericResponse<String>> responseEntity = appointmentController.updateAppointment(appointmentRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(Objects.requireNonNull(responseEntity.getBody()).error());
        assertEquals("Title, Email, Date or ID is missing or End Date is before Start Date!",
                responseEntity.getBody().error().errorMessage());
        verify(appointmentRequest, times(2)).isValid(appointmentRequest.id());
        verify(appointmentService, never()).update(appointmentRequest);
    }

    @Test
    void updateAppointment_shouldReturnNotFound_whenAppointmentNotFoundForAuthor() {

        when(appointmentRequest.isValid(appointmentRequest.id())).thenReturn(true);
        when(appointmentService.update(appointmentRequest)).thenReturn(false);

        ResponseEntity<GenericResponse<String>> responseEntity = appointmentController.updateAppointment(appointmentRequest);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(Objects.requireNonNull(responseEntity.getBody()).error());
        assertEquals("No Appointments found for the given Author!",
                responseEntity.getBody().error().errorMessage());
        verify(appointmentRequest, times(2)).isValid(appointmentRequest.id());
        verify(appointmentService, times(1)).update(appointmentRequest);
    }
}
