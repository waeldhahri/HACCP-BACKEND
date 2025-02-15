package com.example.haccpbackend.modulFridgeTempurature;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FridgeTemperatureService {


    private static final double TEMPERATURE_THRESHOLD = 5.0;
    private static final String ALERT_EMAIL = "waeldhahri3@gmail.com";

    @Autowired
    private FridgeTemperatureRepository repository;


    @Autowired
    private EmailService emailService;


    public FridgeTemperature saveTemperature(String fridgeName, double temperature) {

            FridgeTemperature temp = new FridgeTemperature.Builder()
                .fridgeName(fridgeName)
                .temperature(temperature)
                    .maxThreshold(10.0)
                    .minThreshold(2.0)
                    .timestamp(LocalDateTime.now())
                .build();

        return repository.save(temp);
    }


    @Scheduled(fixedRate = 60000) // Vérifie toutes les minutes
    public void checkTemperatures() {
        List<FridgeTemperature> highTemps = repository.findByTemperatureGreaterThan(TEMPERATURE_THRESHOLD);
        if (!highTemps.isEmpty()) {
            emailService.sendAlert(ALERT_EMAIL, "Alerte Température Frigo", "Un frigo dépasse le seuil de température !");
        }
    }

}
