package com.boisbarganhados.license_plate_api.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PlateOCRFailedException extends Exception {

    private static final long serialVersionUID = 3L;
    public static final String ERROR = "OCR could not read the given plate or the image confidence is too low. Please try again!";

    public PlateOCRFailedException(String message) {
        super(message);
    }
}
