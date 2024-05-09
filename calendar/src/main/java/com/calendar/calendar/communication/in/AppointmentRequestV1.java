package com.calendar.calendar.communication.in;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;

public record AppointmentRequestV1 (String title, String author, Timestamp startDateTime, Timestamp endDateTime, String description) {
    public boolean isValid() {
        return StringUtils.isNotBlank(this.title) &&
                StringUtils.isNotBlank(this.author) &&
                (this.startDateTime != null) &&
                (this.endDateTime != null);
    }
}
