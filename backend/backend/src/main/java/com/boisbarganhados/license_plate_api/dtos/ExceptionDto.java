package com.boisbarganhados.license_plate_api.dtos;

import org.springframework.http.HttpStatus;

import lombok.NonNull;

public record ExceptionDto(
        @NonNull HttpStatus status,
        String message) {
}