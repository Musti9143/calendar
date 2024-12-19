package com.calendar.calendar.motests.communication.in;

import com.calendar.calendar.communication.in.UserRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserRequestTest {

    @ParameterizedTest
    @CsvSource({
            "John, Doe, john.doe@example.com, true",
            "'', Doe, john.doe@example.com, false",
            "John, '', john.doe@example.com, false",
            "John, Doe, '', false"
    })
    void isValid_shouldReturnExpectedResult(String name, String surname, String email, boolean expectedResult) {
        UserRequest request = new UserRequest(name, surname, email);
        assertEquals(expectedResult, request.isValid());
    }
}
