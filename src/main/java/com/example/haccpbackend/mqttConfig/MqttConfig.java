package com.example.haccpbackend.mqttConfig;

import com.example.haccpbackend.modulTepuratureFrigo.tempurature.MqttDataService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLSocketFactory;


@Service
@Slf4j
public class MqttConfig {


    private final MqttDataService mqttDataService;

    public MqttConfig(MqttDataService mqttDataService) {
        this.mqttDataService = mqttDataService;
    }

    @Value("${mqtt.broker}")
    private String brokerUrl;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Value("${mqtt.clientId}")
    private String clientId;

    @Value("${mqtt.topic}")
    private String topic;

    private MqttClient client;

    @PostConstruct
    public void connectAndSubscribe() {
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setCleanSession(true);
            options.setAutomaticReconnect(true);

            client = new MqttClient(brokerUrl, clientId);
            client.connect(options);

            System.out.println("✅ Connecté au broker MQTT : " + brokerUrl);

            client.subscribe(topic, (receivedTopic, message) -> {
                String payload = new String(message.getPayload());
                System.out.println("📩 Message reçu sur " + receivedTopic + " : " + payload);




                // ➡️ Ici tu peux parser le JSON et enregistrer en base
                // Exemple : saveTemperature(payload);

                mqttDataService.processMqttMessage(payload);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void disconnect() {
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
                System.out.println("MQTT déconnecté.");
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }



    public void publishMessage(String payload) {
        try {
            MqttClient client = new MqttClient(brokerUrl, clientId + System.currentTimeMillis());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
            options.setCleanSession(true);

            client.connect(options);

            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(1); // QoS 0,1,2
            client.publish(topic, message);

            System.out.println("✅ Message envoyé sur topic [" + topic + "] : " + payload);
            client.disconnect();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
















}