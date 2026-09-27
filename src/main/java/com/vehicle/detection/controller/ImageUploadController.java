package com.vehicle.detection.controller;

import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.DetectedObjects;
import com.vehicle.detection.entity.VehicleDetection;
import com.vehicle.detection.service.VehicleDetectionModelService;
import com.vehicle.detection.service.VehicleDetectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageUploadController {

    private final VehicleDetectionModelService modelService;
    private final VehicleDetectionService vehicleDetectionService;

    public ImageUploadController(
            VehicleDetectionModelService modelService,
            VehicleDetectionService vehicleDetectionService) {

        this.modelService = modelService;
        this.vehicleDetectionService = vehicleDetectionService;
    }

    @PostMapping("/detect")
    public ResponseEntity<?> detectVehicles(
            @RequestParam("image") MultipartFile imageFile) {

        if (imageFile.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Please select an image.");
        }

        try {

            // Convert uploaded file into a DJL Image
            Image image = ImageFactory.getInstance()
                    .fromInputStream(imageFile.getInputStream());

            // Run AI object detection
            DetectedObjects detections =
                    modelService.detect(image);

            int carCount = 0;
            int busCount = 0;
            int truckCount = 0;
            int motorcycleCount = 0;

            // Count only vehicles
            for (Classifications.Classification object :
                    detections.items()) {

                String className =
                        object.getClassName().toLowerCase();

                switch (className) {

                    case "car":
                        carCount++;
                        break;

                    case "bus":
                        busCount++;
                        break;

                    case "truck":
                        truckCount++;
                        break;

                    case "motorcycle":
                        motorcycleCount++;
                        break;

                    default:
                        break;
                }
            }

            // Create database entity
            VehicleDetection detection =
                    new VehicleDetection();

            detection.setImageName(
                    imageFile.getOriginalFilename()
            );

            detection.setCarCount(carCount);
            detection.setBusCount(busCount);
            detection.setTruckCount(truckCount);
            detection.setMotorcycleCount(motorcycleCount);

            // Service calculates total + time and saves to MySQL
            VehicleDetection savedDetection =
                    vehicleDetectionService.saveDetection(detection);

            return ResponseEntity.ok(savedDetection);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body(
                            "Vehicle detection failed: "
                                    + e.getMessage()
                    );
        }
    }
}