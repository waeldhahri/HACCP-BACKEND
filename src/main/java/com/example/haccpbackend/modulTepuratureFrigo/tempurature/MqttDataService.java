package com.example.haccpbackend.modulTepuratureFrigo.tempurature;

import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
import com.example.haccpbackend.modulTepuratureFrigo.FrigoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service

public class MqttDataService {

    private final FrigoRepository frigoRepository;
    private final TemperatureFrigoRepository temperatureFrigoRepository;




    private final ObjectMapper objectMapper = new ObjectMapper();


    public MqttDataService(FrigoRepository frigoRepository, TemperatureFrigoRepository temperatureFrigoRepository) {
        this.frigoRepository = frigoRepository;
        this.temperatureFrigoRepository = temperatureFrigoRepository;
    }

    public void processMqttMessage(String payload) {
        try {
            JsonNode jsonNode = objectMapper.readTree(payload);

            String deviceId = jsonNode.get("device_id").asText();
            Double temperature = jsonNode.get("temperature").asDouble();
            Integer batteryLevel = jsonNode.get("battery_level").asInt();
            String datetimeStr = jsonNode.get("datetime").asText();

            LocalDateTime datetime = LocalDateTime.parse(datetimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // 🔎 Récupérer le frigo lié au device_id
            Frigo frigo = frigoRepository.findByDeviceId(deviceId)
                    .orElseThrow(() -> new RuntimeException("Frigo non trouvé pour device_id=" + deviceId));

            // 💾 Sauvegarder la température
            TemperatureFrigoMqTT temp = new TemperatureFrigoMqTT();
            temp.setTemperature(temperature);
            temp.setBatteryLevel(batteryLevel);
            temp.setDatetime(datetime);
            temp.setFrigo(frigo);


            temperatureFrigoRepository.save(temp);

            System.out.println("✅ Température sauvegardée : " + temperature + "°C pour frigo " + frigo.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}