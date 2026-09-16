package com.vehicle.detection.repository;

import com.vehicle.detection.entity.VehicleDetection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleDetectionRepository
        extends JpaRepository<VehicleDetection, Long> {
}