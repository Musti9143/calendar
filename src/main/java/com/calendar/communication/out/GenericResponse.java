package com.calendar.communication.out;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record GenericResponse<R>(R response, ErrorResponse error) {

    public static <R> GenericResponse<R> success(R response) {

        return new GenericResponse<>(response, null);
    }

    public static <R> GenericResponse<R> error(ErrorResponse error) {

        return new GenericResponse<>(null, error);
    }
}
