package com.vehicle.detection.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle_detections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDetection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageName;

    private int carCount;

    private int busCount;

    private int truckCount;

    private int motorcycleCount;

    private int totalVehicles;

    private LocalDateTime detectedAt;
}