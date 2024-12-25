package com.calendar.communication.in;

import org.apache.commons.lang3.StringUtils;

public record UserRequest(String name, String surname, String email) {

    public boolean isValid() {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        return StringUtils.isNotBlank(this.name) &&
                StringUtils.isNotBlank(this.surname) &&
                StringUtils.isNotBlank(this.email) &&
                this.email.matches(emailRegex);
    }
}
