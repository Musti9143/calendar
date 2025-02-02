package com.calendar.services;

import com.calendar.communication.in.AppointmentRequest;
import com.calendar.entities.Appointment;
import com.calendar.entities.User;
import com.calendar.mapper.AppointmentMapper;
import com.calendar.repositories.IAppointmentRepository;
import com.calendar.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private IAppointmentRepository appointmentRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentService appointmentService;

    private AppointmentRequest appointmentRequest;
    private Appointment appointment;
    private List<Appointment> appointments;
    private User user;
    private static final String EMAIL = "max.power@email.com";
    private Appointment.Location location;


    @BeforeEach
    void setUp() {
        appointments = new ArrayList<>();
        user = new User("Max", "Power", EMAIL, "123456qwe");
        location = new Appointment.Location("Musterstrasse","123","12345",
                "Musterstadt", "DEU");
        appointmentRequest = new AppointmentRequest("b68eddcf-56f7-47f2-ba0c-ea2cfcfbca27", "Title" ,
                EMAIL, Timestamp.valueOf("2014-01-01 00:00:00"),
                Timestamp.valueOf("2014-01-01 00:00:00"), "description", location);
        appointment = new Appointment("Title", user, Timestamp.valueOf("2014-01-01 00:00:00"),
                Timestamp.valueOf("2014-01-01 00:00:00"), "description", location);
        appointments.add(appointment);
    }

    @Test
    void create_shouldReturnTrue_whenAppointmentCouldBeSaved() {

        when(userRepository.findByEmail(appointmentRequest.email())).thenReturn(user);
        when(appointmentMapper.toAppointment(appointmentRequest,user)).thenReturn(appointment);

        boolean result = appointmentService.create(appointmentRequest);

        assertTrue(result);
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void create_shouldReturnFalse_whenUserDoesNotExist() {

        when(userRepository.findByEmail(appointmentRequest.email())).thenReturn(null);

        boolean result = appointmentService.create(appointmentRequest);

        assertFalse(result);

        verify(appointmentMapper, never()).toAppointment(appointmentRequest,user);
        verify(appointmentRepository, never()).save(appointment);
    }

    @Test
    void deleteById_shouldReturnTrue_whenAppointmentCouldBeDeleted() {

        when(appointmentRepository.findById(UUID.fromString(appointmentRequest.id()))).
                thenReturn(Optional.ofNullable(appointment));

        boolean result = appointmentService.deleteById(UUID.fromString(appointmentRequest.id()));

        assertTrue(result);
        verify(appointmentRepository, times(1)).deleteById(UUID.
                fromString(appointmentRequest.id()));

    }

    @Test
    void deleteById_shouldReturnFalse_whenAppointmentIdCouldNotBeFound() {

        when(appointmentRepository.findById(UUID.fromString(appointmentRequest.id()))).
                thenReturn(Optional.empty());

        boolean result = appointmentService.deleteById(UUID.fromString(appointmentRequest.id()));

        assertFalse(result);
        verify(appointmentRepository, never()).deleteById(UUID.fromString(appointmentRequest.id()));

    }

    @Test
    void update_shouldReturnTrue_whenAppointmentIsUpdated() {

        when(appointmentRepository.findById(UUID.fromString(appointmentRequest.id()))).
                thenReturn(Optional.ofNullable(appointment));

        boolean result = appointmentService.update(appointmentRequest);

        assertTrue(result);
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void update_shouldReturnFalse_whenAppointmentCouldNotBeFound() {

        when(appointmentRepository.findById(UUID.fromString(appointmentRequest.id()))).
                thenReturn(Optional.empty());

        boolean result = appointmentService.update(appointmentRequest);

        assertFalse(result);
        verify(appointmentRepository, never()).save(appointment);
    }

    @Test
    void update_shouldReturnFalse_whenEmailDoesNotMatch() {

        User diffrentUser = new User("Diffrent", "User", "email", "123456qwe");
        appointment.setAuthor(diffrentUser);
        when(appointmentRepository.findById(UUID.fromString(appointmentRequest.id()))).
                thenReturn(Optional.ofNullable(appointment));

        boolean result = appointmentService.update(appointmentRequest);

        assertFalse(result);
        verify(appointmentRepository, never()).save(appointment);
    }

    @Test
    void findByEmail_shouldReturnListOfAppointment_whenUserExists() {

        // GIVEN
        when(userRepository.findByEmail(EMAIL)).thenReturn(user);
        when(appointmentRepository.findByAuthor(user)).thenReturn(appointments);

        // WHEN
        List<Appointment> result = appointmentService.findByEmail(EMAIL);

        // THEN
        assertNotNull(result);
        assertEquals(result.get(0), appointments.get(0));

        verify(userRepository, times(1)).findByEmail(EMAIL);
        verify(appointmentRepository, times(1)).findByAuthor(user);
    }

    @Test
    void findByEmail_shouldReturnNull_whenUserDoesNotExist() {

        // GIVEN
        when(userRepository.findByEmail(EMAIL)).thenReturn(null);

        // WHEN
        List<Appointment> result = appointmentService.findByEmail(EMAIL);

        // THEN
        assertNull(result);

        verify(userRepository, times(1)).findByEmail(EMAIL);
        verify(appointmentRepository, never()).findByAuthor(user);
    }
}
