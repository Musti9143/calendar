package com.calendar.communication.in;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record AppointmentRequest(String title, String author, Timestamp startDateTime, Timestamp endDateTime,
                                 String description) {
    public boolean isValid() {

        LocalDateTime now = LocalDateTime.now();

        if (this.title == null || this.title.isEmpty() || this.author == null || this.author.isEmpty()) {
            return false;
        }
        return this.startDateTime != null && this.endDateTime != null && this.startDateTime.before(this.endDateTime) &&
                this.startDateTime.after(Timestamp.valueOf(now));
    }
}
