package com.vehicle.detection.service;

import ai.djl.Application;
import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import org.springframework.stereotype.Service;
import ai.djl.engine.Engine;

import java.io.IOException;

@Service
public class VehicleDetectionModelService {

    private ZooModel<Image, DetectedObjects> model;

    public VehicleDetectionModelService() {
        loadModel();
    }

    private void loadModel() {
        try {

            System.out.println(
                    "Default DJL Engine: " +
                            Engine.getInstance().getEngineName()
            );

            for (String engineName : Engine.getAllEngines()) {
                System.out.println(
                        "Available DJL Engine: " + engineName
                );
            }

            Criteria<Image, DetectedObjects> criteria =
                    Criteria.builder()
                            .optApplication(Application.CV.OBJECT_DETECTION)
                            .setTypes(Image.class, DetectedObjects.class)
                            .optEngine("OnnxRuntime")
                            .optProgress(new ProgressBar())
                            .build();

            model = criteria.loadModel();

            System.out.println(
                    "Vehicle detection model loaded successfully."
            );

        } catch (IOException | ModelException e) {
            throw new RuntimeException(
                    "Failed to load vehicle detection model", e
            );
        }
    }

    public DetectedObjects detect(Image image)
            throws TranslateException {

        try (Predictor<Image, DetectedObjects> predictor =
                     model.newPredictor()) {

            return predictor.predict(image);
        }
    }
}