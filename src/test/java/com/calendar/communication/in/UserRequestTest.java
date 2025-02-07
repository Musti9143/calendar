package com.calendar.communication.in;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRequestTest {

    @CsvSource({
            "Max, Muster, Max.Muster@email.com, 123456qwe, true",
            "'', Muster, Max.Muster@email.com, 123456qwe, false",
            "Max, '', Max.Muster@email.com, 123456qwe, false",
            "Max, Muster, '', 123456qwe, false",
            "Max, Muster, Max.Musteremail.com, 123456qwe, false",
            "Max, Muster, Max..Muster@email.com, 123456qwe, false",
            "Max, Muster, Max.Muster.@email.com, 123456qwe, false",
            "Max, Muster, Max.Muster@.email.com, 123456qwe, false",
            "Max, Muster, Mäx.Müster@emäil.com, 123456qwe, true"
    })
    @ParameterizedTest
    void isValid_shouldReturnValidResult(String name, String surname, String email, String password, boolean expected) {
        UserRequest userRequest = new UserRequest(name, surname, email, password);
        assertEquals(expected, userRequest.isValid());
    }
}
