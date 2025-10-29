package com.example.haccpbackend.modulTepuratureFrigo.tempurature;

import com.example.haccpbackend.modulTepuratureFrigo.tempurature.TemperatureFrigoMqTT;
import com.example.haccpbackend.modulTepuratureFrigo.tempurature.TemperatureFrigoService;
import jakarta.transaction.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
/*
@RestController
@RequestMapping("/temperature-frigo")
@CrossOrigin(origins = "*")
public class TemperatureFrigoController {

    private final TemperatureFrigoService service;

    public TemperatureFrigoController(TemperatureFrigoService service) {
        this.service = service;
    }

    // 🔹 GET all
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public List<TemperatureFrigoMqTT> getAll() {
        return service.getAllTemperatures();
    }

    // 🔹 GET by Frigo
    @GetMapping("/frigo/{frigoId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public List<TemperatureFrigoMqTT> getByFrigo(@PathVariable Long frigoId) {
        return service.getTemperatureByFrigo(frigoId);
    }

    // 🔹 GET by Organisation
    @GetMapping("/organisation/{organisationId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public List<TemperatureFrigoMqTT> getByOrganisation(@PathVariable Long organisationId) {
        return service.getTemperatureByOrganisation(organisationId);
    }

    // 🔹 GET by Date
    @GetMapping("/date")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public List<TemperatureFrigoMqTT> getByDate(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getTemperatureByDate(date);
    }

    @GetMapping("/between")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public List<TemperatureFrigoMqTT> getBetweenDates(
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return service.getTemperatureBetweenDates(start, end);
    }
}

*/