package com.boisbarganhados.license_plate_api.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boisbarganhados.license_plate_api.models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    public Optional<Vehicle> findById(UUID id);

    public Optional<Vehicle> findByLicensePlate(String licensePlate);

    public Optional<List<Vehicle>> findByVehicleOwnerId(UUID vehicleOwnerId);
}