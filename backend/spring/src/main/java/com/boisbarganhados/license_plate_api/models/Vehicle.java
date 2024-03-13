package com.boisbarganhados.license_plate_api.models;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false, length = 10, unique = true)
    private String licensePlate;

    @Column(nullable = false, length = 100)
    private String licensePlateCountry;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isMercosul;

    @Column(nullable = false, length = 255)
    private String model;

    @Column(nullable = false, length = 255)
    private String color;

    @Column(nullable = false, length = 255)
    private String brand;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int year;

    @OneToMany(mappedBy = "vehicle")
    private List<Fine> fines;

    @OneToMany(mappedBy = "vehicle")
    private List<Parking> parkings;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_owner_id", referencedColumnName = "id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VehicleOwner vehicleOwner;

}
