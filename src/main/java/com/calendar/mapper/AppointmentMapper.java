package com.calendar.mapper;

import com.calendar.communication.in.AppointmentRequest;
import com.calendar.communication.out.AppointmentResponse;
import com.calendar.entities.Appointment;
import com.calendar.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AppointmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "user", target = "author") // Stellt Verbindung zwischen User und Author her
    Appointment toAppointment(final AppointmentRequest appointmentRequest, final User user);

    @Mapping(source = "appointment.author.email", target = "email") // Spezifisch für den Email-Zusammenhang
    AppointmentResponse toAppointmentResponse(final Appointment appointment);

    // Mapping zwischen AppointmentRequest und Appointment.
    @Mapping(target = "id", ignore = true)  // Ignoriere nicht zu ändernde Felder
    @Mapping(target = "author", ignore = true) // Ignoriere das Author-Feld
    @Mapping(target = "location", ignore = true)
    Appointment updateAppointment(AppointmentRequest appointmentRequest, @MappingTarget Appointment appointment);

    @Mapping(source = "appointment.author.email", target = "email")
    List<AppointmentResponse> toAppointmentResponseList(final List<Appointment> appointments);
}
