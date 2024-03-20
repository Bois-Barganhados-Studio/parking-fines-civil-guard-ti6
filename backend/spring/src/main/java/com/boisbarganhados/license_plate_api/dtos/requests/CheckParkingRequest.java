package com.boisbarganhados.license_plate_api.dtos.requests;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CheckParkingRequest(
                @NotBlank @Length(min = 4, max = 14) String latitude,

                @NotBlank @Length(min = 4, max = 14) String longitude,

                @NotNull MultipartFile picture,

                UUID rotating_place_id) {
}