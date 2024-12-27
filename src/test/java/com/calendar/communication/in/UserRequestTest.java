package com.calendar.communication.in;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRequestTest {

    @CsvSource({
            "Max, Muster, Max.Muster@email.com, true",
            "'', Muster, Max.Muster@email.com, false",
            "Max, '', Max.Muster@email.com, false",
            "Max, Muster, '', false",
            "Max, Muster, Max.Musteremail.com, false",
            "Max, Muster, Max..Muster@email.com, false",
            "Max, Muster, Max.Muster.@email.com, false",
            "Max, Muster, Max.Muster@.email.com, false",
            "Max, Muster, Mäx.Müster@emäil.com, true"
    })
    @ParameterizedTest
    void isValid_shouldReturnValidResult(String name, String surname, String email, boolean expected){
        UserRequest userRequest = new UserRequest(name, surname, email);
        assertEquals(expected, userRequest.isValid());
    }
}
