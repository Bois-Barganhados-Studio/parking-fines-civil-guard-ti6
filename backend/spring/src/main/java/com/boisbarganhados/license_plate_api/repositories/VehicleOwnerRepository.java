package com.boisbarganhados.license_plate_api.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boisbarganhados.license_plate_api.models.VehicleOwner;

public interface VehicleOwnerRepository extends JpaRepository<VehicleOwner, UUID> {
    public Optional<VehicleOwner> findById(UUID id);
}