package com.calendar.communication.in;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.UUID;

public record AppointmentRequest(String id, String title, String email, Timestamp startDateTime, Timestamp endDateTime,
                                 String description) {
    public boolean isValid() {
        //TODO : check startdate and enddate are not in past

        return StringUtils.isNotBlank(this.title) &&
                StringUtils.isNotBlank(this.email) &&
                (this.startDateTime != null) &&
                (this.endDateTime != null);
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
