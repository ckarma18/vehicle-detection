package com.vehicle.detection.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageUploadController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("image") MultipartFile image) {

        if (image.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Please select an image.");
        }

        return ResponseEntity.ok(
                "Image uploaded successfully: " +
                        image.getOriginalFilename()
        );
    }
}