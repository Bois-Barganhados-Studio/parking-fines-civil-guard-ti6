package com.boisbarganhados.license_plate_api.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.boisbarganhados.license_plate_api.models.RotatingPlace;
import java.util.List;

public interface RotatingPlaceRepository extends JpaRepository<RotatingPlace, UUID> {
    public Optional<RotatingPlace> findById(UUID id);

    public Optional<List<RotatingPlace>> findByStreet(String street);

    @Query("SELECT r FROM RotatingPlace r WHERE r.startLatitude <= ?1 AND r.endLatitude >= ?1 AND r.startLongitude <= ?2 AND r.endLongitude >= ?2")
    public Optional<RotatingPlace> findByCoordinates(String latitude, String longitude);

}
