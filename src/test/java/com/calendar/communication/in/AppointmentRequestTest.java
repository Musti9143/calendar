package com.calendar.communication.in;


import com.calendar.entities.Location;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AppointmentRequestTest {

    @CsvSource({

            "'', Title, Max.Muster@email.com, 2024-12-20 00:51:00.000, " +
                    "2024-12-21 00:51:00.000, description, Mönchengladbach, Neusser Str., DEU, 41065, 46, true",
            "'', Title, Max.Muster@email.com, 2024-12-24 00:51:00.000, " +
                    "2024-12-21 00:51:00.000, description, Mönchengladbach, Neusser Str., DEU, 41065, 46, false",
            "'', Title, Max.Muster@email.com, , " +
                    "2024-12-21 00:51:00.000, description, Mönchengladbach, Neusser Str., DEU, 41065, 46, false",
            "'', Title, Max.Muster@email.com, 2024-12-20 00:51:00.000, " +
                    ", description, Mönchengladbach, Neusser Str., DEU, 41065, 46, false",
            "'', '', Max.Muster@email.com, 2024-12-20 00:51:00.000, " +
                    "2024-12-21 00:51:00.000, description, Mönchengladbach, Neusser Str., DEU, 41065, 46, false",
            "'', Title, Max..Muster@email.com, 2024-12-20 00:51:00.000, " +
                    "2024-12-21 00:51:00.000, description, Mönchengladbach, Neusser Str., DEU, 41065, 46, false",
            "'', Title, '', 2024-12-20 00:51:00.000, " +
                    "2024-12-21 00:51:00.000, description, Mönchengladbach, Neusser Str., DEU, 41065, 46, false",
            "'', Title, Max.Muster@email.com, 2024-12-20 00:51:00.000, " +
                    "2024-12-21 00:51:00.000, description, Mönchengladbach, Neusser Str., DEU, 41065, 46, true",
            "'', Title, Max.Muster@email.com, 2024-12-20 00:51:00.000, " +
                    "2024-12-21 00:51:00.000, description, '', Neusser Str., DEU, 41065, 46, false"
    })

    @ParameterizedTest
    void isValid_shouldReturnValidResult(String id, String title, String email, Timestamp startDateTime,
                                         Timestamp endDateTime, String description, String city, String street,
                                         String country, String postalCode, String streetNumber, boolean expected) {

        AppointmentRequest appointmentRequest = new AppointmentRequest(id,title,email,startDateTime,endDateTime,
                description, new Location(city, street, country, postalCode, streetNumber));
        assertEquals(expected,appointmentRequest.isAllValid());
    }

    @CsvSource({

            "b68eddcf-56f7-47f2-ba0c-ea2cfcfbca27, Title, Max.Muster@email.com, 2024-12-20 00:51:00.000, " +
                    "2024-12-21 00:51:00.000, description, true",
            "b68eddcf-56f7-47f2-ba0c-ea2cfcfbca27, '', Max.Muster@email.com, 2024-12-20 00:51:00.000, " +
                    "2024-12-21 00:51:00.000, description, false",
            "'', Title, Max.Muster@email.com, 2024-12-20 00:51:00.000, " +
                    "2024-12-21 00:51:00.000, description, false"
    })

    @ParameterizedTest
    void isValidWithParameter_shouldReturnValidResult(String id, String title, String email, Timestamp startDateTime,
                                         Timestamp endDateTime, String description, boolean expected) {

        AppointmentRequest appointmentRequest = new AppointmentRequest(id,title,email,startDateTime,endDateTime,
                description, null);
        assertEquals(expected,appointmentRequest.isValid(id));
    }
}
