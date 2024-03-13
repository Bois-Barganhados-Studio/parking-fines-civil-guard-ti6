package com.boisbarganhados.license_plate_api.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boisbarganhados.license_plate_api.models.Parking;

public interface ParkingRepository extends JpaRepository<Parking, UUID> {
    public Optional<Parking> findById(UUID id);

    public Optional<List<Parking>> findByVehicleId(UUID vehicleId);

    public Optional<List<Parking>> findByRotatingPlaceId(UUID rotatingPlaceId);

}
