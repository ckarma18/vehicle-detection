package com.vehicle.detection.service;

import com.vehicle.detection.entity.VehicleDetection;
import com.vehicle.detection.repository.VehicleDetectionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VehicleDetectionService {

    private final VehicleDetectionRepository vehicleDetectionRepository;

    public VehicleDetectionService(VehicleDetectionRepository vehicleDetectionRepository) {
        this.vehicleDetectionRepository = vehicleDetectionRepository;
    }

    public VehicleDetection saveDetection(VehicleDetection detection) {
        detection.setDetectedAt(LocalDateTime.now());

        int total =
                detection.getCarCount()
                        + detection.getBusCount()
                        + detection.getTruckCount()
                        + detection.getMotorcycleCount();

        detection.setTotalVehicles(total);

        return vehicleDetectionRepository.save(detection);
    }

    public List<VehicleDetection> getAllDetections() {
        return vehicleDetectionRepository.findAll();
    }
}