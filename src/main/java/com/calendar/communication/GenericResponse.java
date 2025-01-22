package com.calendar.communication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GenericResponse<T> {

    private boolean result;
    private String message;
    private T data;
}
