package com.boisbarganhados.license_plate_api.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "parkings")
public class Parking {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime parkingDate;

    @Column(nullable = true)
    private LocalDateTime exitDate;

    @Column(nullable = false, length = 14)
    private String latitude;

    @Column(nullable = false, length = 14)
    private String longitude;

    @OneToOne(mappedBy = "parking")
    private Fine fine;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Vehicle vehicle;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "rotating_place_id", referencedColumnName = "id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RotatingPlace rotatingPlace;
}
