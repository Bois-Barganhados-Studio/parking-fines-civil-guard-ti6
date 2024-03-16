package com.boisbarganhados.license_plate_api.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.boisbarganhados.license_plate_api.models.enums.OperationDayRule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rotating_places")
public class RotatingPlace {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false, length = 14)
    private String startLatitude;

    @Column(nullable = false, length = 14)
    private String startLongitude;

    @Column(nullable = false, length = 14)
    private String endLatitude;

    @Column(nullable = false, length = 14)
    private String endLongitude;

    @Column(nullable = false)
    private long bhParkingId;

    @Column(nullable = false)
    private int physicalVacancies;

    @Column(nullable = false)
    private int rotatingVacancies;

    @Column(nullable = true)
    private int parkTime;

    @Column(nullable = false, length = 255)
    private String street;

    @Column(nullable = true, length = 255)
    private String streetReference;

    @Column(nullable = false, length = 100)
    private String neighborhood;

    @Column(nullable = false)
    private LocalDateTime startOperationPeriodTime;

    @Column(nullable = false)
    private LocalDateTime endOperationPeriodTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationDayRule operationDayRule;

    @OneToMany(mappedBy = "rotatingPlace")
    private List<Parking> parkings;
}