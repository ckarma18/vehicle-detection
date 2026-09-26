package com.vehicle.detection.controller;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.DetectedObjects;
import com.vehicle.detection.service.VehicleDetectionModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ai.djl.modality.Classifications;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
public class ImageUploadController {

    private final VehicleDetectionModelService modelService;

    public ImageUploadController(
            VehicleDetectionModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping("/detect")
    public ResponseEntity<?> detectVehicles(
            @RequestParam("image") MultipartFile imageFile) {

        if (imageFile.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Please select an image.");
        }

        try {

            Image image = ImageFactory.getInstance()
                    .fromInputStream(imageFile.getInputStream());

            DetectedObjects detections =
                    modelService.detect(image);

            List<Map<String, Object>> detectedObjects =
                    new ArrayList<>();

            for (Classifications.Classification object :
                    detections.items()) {

                Map<String, Object> detectedObject =
                        new HashMap<>();

                detectedObject.put(
                        "className",
                        object.getClassName()
                );

                detectedObject.put(
                        "probability",
                        object.getProbability()
                );

                detectedObjects.add(detectedObject);
            }

            Map<String, Object> response = new HashMap<>();

            response.put(
                    "imageName",
                    imageFile.getOriginalFilename()
            );

            response.put(
                    "totalDetected",
                    detectedObjects.size()
            );

            response.put(
                    "detections",
                    detectedObjects
            );

            return ResponseEntity.ok(response);

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