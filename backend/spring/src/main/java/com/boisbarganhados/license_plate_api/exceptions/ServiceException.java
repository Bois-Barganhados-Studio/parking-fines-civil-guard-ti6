package com.boisbarganhados.license_plate_api.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ServiceException extends Exception {

    private static final long serialVersionUID = 2L;

    public ServiceException(String message) {
        super(message);
    }
}
