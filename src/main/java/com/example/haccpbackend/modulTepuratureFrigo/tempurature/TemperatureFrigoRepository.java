package com.example.haccpbackend.modulTepuratureFrigo.tempurature;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TemperatureFrigoRepository extends JpaRepository<TemperatureFrigoMqTT,Long> {

    // Par Frigo
    List<TemperatureFrigoMqTT> findByFrigoId(Long frigoId);

    // Par Organisation
    List<TemperatureFrigoMqTT> findByOrganisationId(Long organisationId);

    // Par date exacte
    List<TemperatureFrigoMqTT> findByDatetime(LocalDateTime datetime);

    // Entre deux dates
    List<TemperatureFrigoMqTT> findByDatetimeBetween(LocalDateTime start, LocalDateTime end);

}

