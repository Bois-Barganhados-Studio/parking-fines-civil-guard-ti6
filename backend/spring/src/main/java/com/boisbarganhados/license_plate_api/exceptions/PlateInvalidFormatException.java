package com.boisbarganhados.license_plate_api.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PlateInvalidFormatException extends Exception {

    private static final long serialVersionUID = 5L;
    public static final String ERROR = "License plate format is invalid and could not be fixed. Please try again!";

    public PlateInvalidFormatException(String message) {
        super(message);
    }
}
