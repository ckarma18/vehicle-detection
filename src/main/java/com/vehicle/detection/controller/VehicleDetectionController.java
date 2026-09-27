package com.vehicle.detection.controller;

import com.vehicle.detection.entity.VehicleDetection;
import com.vehicle.detection.service.VehicleDetectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detections")
public class VehicleDetectionController {

    private final VehicleDetectionService vehicleDetectionService;

    public VehicleDetectionController(
            VehicleDetectionService vehicleDetectionService) {
        this.vehicleDetectionService = vehicleDetectionService;
    }

    // POST - Save a detection
    @PostMapping
    public ResponseEntity<VehicleDetection> createDetection(
            @RequestBody VehicleDetection detection) {

        VehicleDetection savedDetection =
                vehicleDetectionService.saveDetection(detection);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedDetection);
    }

    // GET - Get all detections
    @GetMapping
    public ResponseEntity<List<VehicleDetection>> getAllDetections() {

        return ResponseEntity.ok(
                vehicleDetectionService.getAllDetections()
        );
    }

    // GET - Get one detection by ID
    @GetMapping("/{id}")
    public ResponseEntity<VehicleDetection> getDetectionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                vehicleDetectionService.getDetectionById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDetection(
            @PathVariable Long id) {

        vehicleDetectionService.deleteDetection(id);

        return ResponseEntity.ok(
                "Detection deleted successfully."
        );
    }
}