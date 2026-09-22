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

    private Integer carCount;

    private Integer busCount;

    private Integer truckCount;

    private Integer motorcycleCount;

    private Integer totalVehicles;

    private LocalDateTime detectedAt;
}