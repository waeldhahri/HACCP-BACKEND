package com.example.haccpbackend.modulTepuratureFrigo.tempurature;


import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/mqtt")

public class MqttTestController {

    private final MqttDataService mqttDataService;

    public MqttTestController(MqttDataService mqttDataService) {
        this.mqttDataService = mqttDataService;
    }

    @PostMapping("/simulate")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public String simulate(@RequestBody String jsonPayload) {
        mqttDataService.processMqttMessage(jsonPayload);
        return "✅ Message MQTT simulé : " + jsonPayload;
    }
}
