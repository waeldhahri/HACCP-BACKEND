package com.example.haccpbackend.modulTepuratureFrigo.tempurature.TemperatureFrigoService2;

import com.example.haccpbackend.modulTepuratureFrigo.tempurature.TemperatureFrigoDTO;
import com.example.haccpbackend.modulTepuratureFrigo.tempurature.TemperatureFrigoMqTT;
import jakarta.transaction.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/temperature-frigo")
@CrossOrigin(origins = "*")
public class TemperatureFrigoController {

    private final TemperatureFrigoService2 service;

    public TemperatureFrigoController(TemperatureFrigoService2 service) {
        this.service = service;
    }





    @PostMapping("/add")
    public ResponseEntity<?> addTemperature(@RequestBody TemperatureRequest request) {
        try {
            TemperatureFrigoMqTT savedTemp = service.addTemperature(request);
            return ResponseEntity.ok(savedTemp);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }






    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public List<TemperatureFrigoDTO> getAll() {
        return service.getAllTemperatures();
    }

    @GetMapping("/frigo/{frigoId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public List<TemperatureFrigoDTO> getByFrigo(@PathVariable Long frigoId) {
        return service.getTemperatureByFrigo(frigoId);
    }

    @GetMapping("/organisation/{organisationId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public List<TemperatureFrigoDTO> getByOrganisation(@PathVariable Long organisationId) {
        return service.getTemperatureByOrganisation(organisationId);
    }

    @GetMapping("/date")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public List<TemperatureFrigoDTO> getByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getTemperatureByDate(date);
    }

    @GetMapping("/between")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public List<TemperatureFrigoDTO> getBetweenDates(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return service.getTemperatureBetweenDates(start, end);
    }
}
