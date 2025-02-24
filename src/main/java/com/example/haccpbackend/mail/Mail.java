package com.example.haccpbackend.mail;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@CrossOrigin
public class Mail {



    @Autowired
    private JavaMailSender mailSender;


    @PostMapping("/{email}")
    public void envoyerEmail(@PathVariable  String email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject("WAEL HELLO");
        helper.setText("test email sender");

        mailSender.send(message);
    }
}
