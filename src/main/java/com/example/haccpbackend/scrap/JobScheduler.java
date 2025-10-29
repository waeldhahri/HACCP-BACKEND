package com.example.haccpbackend.scrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobScheduler {

    @Autowired
    private JobScraperService scraperService;

    @Autowired
    private EmailService2 emailService;

    // Toutes les 5 heures (5 * 60 * 60 * 1000 ms)
    //@Scheduled(fixedRate = 18000000)

    /*
    @Scheduled(fixedRate = 60000)
    public void checkJobs() {
        try {
            var jobs = scraperService.getMatchingJobs();
            emailService.sendJobEmail(jobs);
            System.out.println("Email réeusi");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}

