package com.example.haccpbackend.modulFridgeTempurature;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FridgeTemperatureRepository extends JpaRepository<FridgeTemperature, Long> {

    List<FridgeTemperature> findByTemperatureGreaterThan(double threshold);

}
