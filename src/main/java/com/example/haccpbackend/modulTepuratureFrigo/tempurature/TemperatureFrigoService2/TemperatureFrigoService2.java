package com.example.haccpbackend.modulTepuratureFrigo.tempurature.TemperatureFrigoService2;

import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
import com.example.haccpbackend.modulTepuratureFrigo.FrigoRepository;
import com.example.haccpbackend.modulTepuratureFrigo.tempurature.TemperatureFrigoDTO;
import com.example.haccpbackend.modulTepuratureFrigo.tempurature.TemperatureFrigoMqTT;
import com.example.haccpbackend.modulTepuratureFrigo.tempurature.TemperatureFrigoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemperatureFrigoService2 {

    private final TemperatureFrigoRepository repository;
    private final FrigoRepository frigoRepository;

    public TemperatureFrigoService2(TemperatureFrigoRepository repository, FrigoRepository frigoRepository) {
        this.repository = repository;
        this.frigoRepository = frigoRepository;
    }

    //

    public TemperatureFrigoMqTT addTemperature(TemperatureRequest request) {
        Frigo frigo = frigoRepository.findById(request.getFrigoId())
                .orElseThrow(() -> new RuntimeException("Frigo introuvable avec ID: " + request.getFrigoId()));

        TemperatureFrigoMqTT temperature = new TemperatureFrigoMqTT();
        temperature.setFrigo(frigo);
        temperature.setTemperature(request.getTemperature());
        temperature.setBatteryLevel(request.getBatteryLevel());
        temperature.setDatetime(LocalDateTime.now().withSecond(0).withNano(0));

        return repository.save(temperature);
    }












    //

    public List<TemperatureFrigoDTO> getAllTemperatures() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TemperatureFrigoDTO> getTemperatureByFrigo(Long frigoId) {
        return repository.findByFrigoId(frigoId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TemperatureFrigoDTO> getTemperatureByOrganisation(Long organisationId) {
        return repository.findByOrganisationId(organisationId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TemperatureFrigoDTO> getTemperatureByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return repository.findByDatetimeBetween(startOfDay, endOfDay).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TemperatureFrigoDTO> getTemperatureBetweenDates(LocalDate start, LocalDate end) {
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);
        return repository.findByDatetimeBetween(startDateTime, endDateTime).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TemperatureFrigoDTO convertToDTO(TemperatureFrigoMqTT entity) {
        String frigoName = entity.getFrigo() != null ? entity.getFrigo().getName() : null;
        String deviceName = entity.getDevice() != null ? entity.getDevice().getDeviceId() : null;

        return new TemperatureFrigoDTO(
                entity.getId(),
                entity.getTemperature(),
                entity.getBatteryLevel(),
                entity.getDatetime(),
                frigoName,
                deviceName
        );
    }
}
