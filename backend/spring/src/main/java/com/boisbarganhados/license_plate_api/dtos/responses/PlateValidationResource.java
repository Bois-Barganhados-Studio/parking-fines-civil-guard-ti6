package com.boisbarganhados.license_plate_api.dtos.responses;

import lombok.Builder;

@Builder
public record PlateValidationResource(
        String plate,
        String message) {
}