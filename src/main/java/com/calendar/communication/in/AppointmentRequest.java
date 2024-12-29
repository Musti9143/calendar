package com.calendar.communication.in;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;

public record AppointmentRequest(String title, String email, Timestamp startDateTime, Timestamp endDateTime,
                                 String description) {
    public boolean isValid() {
        //TODO : check startdate and enddate are not in past

        return StringUtils.isNotBlank(this.title) &&
                StringUtils.isNotBlank(this.email) &&
                (this.startDateTime != null) &&
                (this.endDateTime != null);
    }
}
