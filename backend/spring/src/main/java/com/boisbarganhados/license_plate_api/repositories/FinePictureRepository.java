package com.boisbarganhados.license_plate_api.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boisbarganhados.license_plate_api.models.FinePicture;

public interface FinePictureRepository extends JpaRepository<FinePicture, UUID> {
    public Optional<FinePicture> findById(UUID id);

    public Optional<FinePicture> findByFineId(UUID fineId);
}
