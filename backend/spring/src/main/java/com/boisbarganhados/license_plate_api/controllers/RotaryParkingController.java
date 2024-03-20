package com.boisbarganhados.license_plate_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rotary-parking")
@RequiredArgsConstructor
public class RotaryParkingController extends BaseController {
    @PostMapping
    public ResponseEntity<PlateValidationResource> rotaryParking(@ModelAttribute @Valid CheckParkingRequest request)
            throws ServiceException, InvalidRequestException, Exception, PlateInvalidFormatException,
            PlateNotFoundException, PlateOCRFailedException {
        return ResponseEntity.ok(new PlateValidationResource("Teste", "Ola deu bom a resposta!"));
    }
}