package com.boisbarganhados.license_plate_api.models;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
@Table(name = "registered_cars")
public class RegisteredCar {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false, length = 10)
    private String licensePlate;

    @Column(nullable = false, length = 100)
    private String licensePlateCountry;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean mercoSul;

    @Column(nullable = false, length = 255)
    private String model;

    @Column(nullable = false, length = 255)
    private String color;

    @Column(nullable = false, length = 255)
    private String brand;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int year;

}
