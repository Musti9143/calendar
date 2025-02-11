package com.calendar.communication.in;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public record UserRequest(String name, String surname, String email, String password) {

    public boolean isValid() {

        boolean isEmailValid = EmailValidator.getInstance().isValid(this.email);
        return StringUtils.isNotBlank(this.name) &&
                StringUtils.isNotBlank(this.surname) &&
                StringUtils.isNotBlank(this.email) &&
                StringUtils.isNotBlank((this.password)) &&
                isEmailValid;
    }
}
