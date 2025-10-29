package com.example.haccpbackend.modulTepuratureFrigo.tempurature;


import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class TemperatureFrigoService {

    private final TemperatureFrigoRepository repository;

    public TemperatureFrigoService(TemperatureFrigoRepository repository) {
        this.repository = repository;
    }

    public List<TemperatureFrigoMqTT> getAllTemperatures() {
        return repository.findAll();
    }

    public List<TemperatureFrigoMqTT> getTemperatureByFrigo(Long frigoId) {
        return repository.findByFrigoId(frigoId);
    }

    public List<TemperatureFrigoMqTT> getTemperatureByOrganisation(Long organisationId) {
        return repository.findByOrganisationId(organisationId);
    }

    public List<TemperatureFrigoMqTT> getTemperatureByDate(LocalDate date) {
        // conversion LocalDate -> LocalDateTime
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return repository.findByDatetimeBetween(startOfDay, endOfDay);
    }

    public List<TemperatureFrigoMqTT> getTemperatureBetweenDates(LocalDate start, LocalDate end) {
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);
        return repository.findByDatetimeBetween(startDateTime, endDateTime);
    }
}