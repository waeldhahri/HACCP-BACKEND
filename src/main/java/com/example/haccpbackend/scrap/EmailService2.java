package com.example.haccpbackend.scrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService2 {

    @Autowired
    private JavaMailSender mailSender;

    public void sendJobEmail(List<String> jobs) {
        if (jobs.isEmpty()) return;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("waeldhahri3@gmail.com");
        message.setSubject("Nouveaux jobs disponibles à Koblenz !");
        message.setText(String.join("\n\n", jobs));

        mailSender.send(message);
    }
}
