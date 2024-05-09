package com.calendar.calendar.communication.in;

import org.apache.commons.lang3.StringUtils;

public record UserRequest(String name, String surname, String email) {

    public boolean isValid() {
        //TODO : check email for valid regex

        return StringUtils.isNotBlank(this.name) &&
                StringUtils.isNotBlank(this.surname) &&
                StringUtils.isNotBlank(this.email);
    }
}
