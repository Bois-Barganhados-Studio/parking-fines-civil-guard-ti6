package com.boisbarganhados.license_plate_api.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.boisbarganhados.license_plate_api.models.Fine;

public interface FineRepository extends JpaRepository<Fine, UUID> {
    public Optional<Fine> findById(UUID id);

    public Optional<List<Fine>> findByVehicleId(UUID vehicleId);

    @Query("SELECT f FROM Fine f JOIN Vehicle v ON f.vehicle.id = v.id WHERE v.vehicleOwner.id = :vehicleOwnerId")
    public Optional<List<Fine>> findByVehicleOwnerId(UUID vehicleOwnerId);
}
