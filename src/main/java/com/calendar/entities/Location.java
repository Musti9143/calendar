package com.calendar.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location implements Serializable {

    private String street;
    private String streetNumber;
    private String postalCode;
    private String city;
    private String country;
}