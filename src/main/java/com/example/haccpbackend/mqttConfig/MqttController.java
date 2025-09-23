package com.example.haccpbackend.mqttConfig;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

    private final MqttConfig mqttConfig;

    public MqttController(MqttConfig mqttConfig) {
        this.mqttConfig = mqttConfig;
    }

    // Exemple : POST /mqtt/send?msg=Hello
    @PostMapping("/send")
    public String sendMessage(@RequestParam String msg) {
        mqttConfig.publishMessage(msg);
        return "Message envoyé : " + msg;
    }
}
