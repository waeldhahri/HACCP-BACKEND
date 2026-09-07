package com.example.haccpbackend.modulTepuratureFrigo.tempurature;

import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
import com.example.haccpbackend.modulTepuratureFrigo.FrigoRepository;
import com.example.haccpbackend.modulTepuratureFrigo.tempurature.device.DeviceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class MqttDataService {

    @Autowired
    private JavaMailSender mailSender;

    private final ObjectMapper objectMapper;
    private final DeviceRepository deviceRepository;
    private final TemperatureFrigoRepository temperatureFrigoMqTTRepository;

    public MqttDataService(ObjectMapper objectMapper, DeviceRepository deviceRepository
            , TemperatureFrigoRepository temperatureFrigoMqTTRepository) {
        this.objectMapper = objectMapper;
        this.deviceRepository = deviceRepository;
        this.temperatureFrigoMqTTRepository = temperatureFrigoMqTTRepository;


    }

    private static final String ALERT_EMAIL = "waeldhahri3@gmail.com";


    public void processMqttMessage(String payload) {
        try {
            JsonNode jsonNode = objectMapper.readTree(payload);

            String deviceId = jsonNode.get("device_id").asText();
            Double temperature = jsonNode.get("temperature").asDouble();
            Integer batteryLevel = jsonNode.get("battery_level").asInt();
            String datetimeStr = jsonNode.get("datetime").asText();

            LocalDateTime datetime = LocalDateTime.parse(
                    datetimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            );

            deviceRepository.findByDeviceId(deviceId).ifPresentOrElse(device -> {
                Frigo frigo = device.getFrigo();

                // 💾 Sauvegarde en base
                TemperatureFrigoMqTT temp = new TemperatureFrigoMqTT();
                temp.setDevice(device);
                temp.setTemperature(temperature);
                temp.setBatteryLevel(batteryLevel);
                temp.setDatetime(datetime);
                temp.setFrigo(frigo);

                temperatureFrigoMqTTRepository.save(temp);

                System.out.println("✅ Température enregistrée pour frigo: " + frigo.getName()
                        + " | device: " + deviceId
                        + " | temp: " + temperature + "°C | battery: " + batteryLevel + "%");

                // 🚨 GESTION DES ALERTES SELON ÉTAT DU FRIGO
                boolean isActive = frigo.isActive();
                boolean tempAlerte = false;

                if (isActive && temperature > 2) {
                    // Frigo actif : alerte si température > 2°C
                    tempAlerte = true;
                } else if (!isActive && temperature > -4) {
                    // Frigo passif : alerte si température > -4°C
                    tempAlerte = true;
                }

                if (tempAlerte) {
                    sendEmail(
                            ALERT_EMAIL,
                            "⚠️ ALERTE Température",
                            "Le frigo '" + frigo.getName() + "' (device: " + deviceId + ") "
                                    + "a atteint une température anormale de " + temperature + "°C à " + datetime + ".\n"
                                    + "État du frigo : " + (isActive ? "Actif (> 2°C)" : "Passif (> -4°C)")
                    );
                    System.out.println("📧 Email d'alerte température envoyé !");
                }

                // 🚨 ALERTE BATTERIE
                if (batteryLevel < 30) {
                    sendEmail(
                            ALERT_EMAIL,
                            "⚠️ ALERTE Batterie faible",
                            "Le device '" + deviceId + "' du frigo '" + frigo.getName() + "' "
                                    + "a un niveau de batterie faible (" + batteryLevel + "%) à " + datetime + "."
                    );
                    System.out.println("📧 Email d'alerte batterie envoyé !");
                }

            }, () -> System.err.println("❌ Aucun device trouvé pour device_id=" + deviceId));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}