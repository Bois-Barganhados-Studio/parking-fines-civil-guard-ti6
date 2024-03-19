package com.boisbarganhados.license_plate_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boisbarganhados.license_plate_api.dtos.requests.CheckParkingRequest;
import com.boisbarganhados.license_plate_api.dtos.responses.PlateValidationResource;
import com.boisbarganhados.license_plate_api.exceptions.InvalidRequestException;
import com.boisbarganhados.license_plate_api.exceptions.PlateInvalidFormatException;
import com.boisbarganhados.license_plate_api.exceptions.PlateNotFoundException;
import com.boisbarganhados.license_plate_api.exceptions.PlateOCRFailedException;
import com.boisbarganhados.license_plate_api.exceptions.ServiceException;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rotary-parking")
@RequiredArgsConstructor
public class RotaryParkingController {
    @PostMapping
    public ResponseEntity<PlateValidationResource> createEmployee(@Valid @RequestBody CheckParkingRequest request)
            throws ServiceException, InvalidRequestException, Exception, PlateInvalidFormatException,
            PlateNotFoundException, PlateOCRFailedException {
        return ResponseEntity.ok(null);
    }
}
