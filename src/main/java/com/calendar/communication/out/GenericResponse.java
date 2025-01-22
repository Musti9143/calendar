package com.calendar.communication.out;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record GenericResponse<R, E>(R response, E error) {

    public static <R, E> GenericResponse<R, E> success(R response) {

        return new GenericResponse<>(response, null);
    }

    public static <R, E> GenericResponse<R, E> error(E error) {

        return new GenericResponse<>(null, error);
    }
}
