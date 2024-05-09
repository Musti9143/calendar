package com.calendar.calendar.communication.in;

import org.apache.commons.lang3.StringUtils;

public record UserRequestV1 (String name, String surname, String email) {

    public boolean isValid() {
        return StringUtils.isNotBlank(this.name) &&
                StringUtils.isNotBlank(this.surname) &&
                StringUtils.isNotBlank(this.email);
    }
}
