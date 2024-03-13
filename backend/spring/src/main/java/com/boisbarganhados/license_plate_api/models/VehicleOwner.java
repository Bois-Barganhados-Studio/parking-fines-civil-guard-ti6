package com.boisbarganhados.license_plate_api.models;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Table(name = "vehicle_owners")
public class VehicleOwner {
    
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false, length = 512)
    private String name;

    @Column(nullable = false, length = 128)
    private String email;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(nullable = true, length = 15)
    private String phone;

    @Column(nullable = false, length = 512)
    private String address;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean hasCnh;

    @OneToMany(mappedBy = "vehicleOwner")
    private List<Vehicle> vehicles;
    
}