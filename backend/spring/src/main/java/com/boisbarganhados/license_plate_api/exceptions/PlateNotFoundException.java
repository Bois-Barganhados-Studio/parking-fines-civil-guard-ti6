package com.boisbarganhados.license_plate_api.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PlateNotFoundException extends Exception{

    private static final long serialVersionUID = 4L;
    public static final String ERROR = "License plate could not be found with the given data (ANPR failed to find a valid plate). Please try again!";

    public PlateNotFoundException(String message) {
        super(message);
    }
}
