package com.calendar.communication.in;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.sql.Timestamp;
import java.util.UUID;

public record AppointmentRequest(String id, String title, String email, Timestamp startDateTime, Timestamp endDateTime,
                                 String description) {
    public boolean isValid() {

        boolean isEmailValid = EmailValidator.getInstance().isValid(this.email);
        return StringUtils.isNotBlank(this.title) &&
                StringUtils.isNotBlank(this.email) &&
                isEmailValid &&
                this.startDateTime != null &&
                this.endDateTime != null &&
                this.startDateTime.before(endDateTime);
    }

    public boolean isValid(String id) {

        try {
            UUID.fromString(id);
        } catch(IllegalArgumentException e) {
            return false;
        }

        return this.isValid();
    }
}
