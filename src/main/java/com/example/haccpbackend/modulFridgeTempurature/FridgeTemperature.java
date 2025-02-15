package com.example.haccpbackend.modulFridgeTempurature;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class FridgeTemperature {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fridgeName;
    private double temperature;
    private double minThreshold;// valeur Seuil minimale
    private double maxThreshold;// valeur Seuil maximale
    private LocalDateTime timestamp;

    // 🔹 Constructeur privé pour empêcher l'instanciation directe
    private FridgeTemperature(Builder builder) {
        this.id = builder.id;
        this.fridgeName = builder.fridgeName;
        this.temperature = builder.temperature;
        this.minThreshold = builder.minThreshold;
        this.maxThreshold = builder.maxThreshold;
        this.timestamp = builder.timestamp;
    }

    public FridgeTemperature(Long id, String fridgeName, double temperature, double minThreshold, double maxThreshold, LocalDateTime timestamp) {
        this.id = id;
        this.fridgeName = fridgeName;
        this.temperature = temperature;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
        this.timestamp = timestamp;
    }

    public FridgeTemperature() {

    }

    // ✅ Implémentation du Builder
    public static class Builder {
        private Long id;
        private String fridgeName;
        private double temperature;
        private double minThreshold;
        private double maxThreshold;

        private LocalDateTime timestamp;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder fridgeName(String fridgeName) {
            this.fridgeName = fridgeName;
            return this;
        }

        public Builder temperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder minThreshold(double minThreshold) {
            this.minThreshold = minThreshold;
            return this;
        }

        public Builder maxThreshold(double maxThreshold) {
            this.maxThreshold = maxThreshold;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public FridgeTemperature build() {
            return new FridgeTemperature(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFridgeName() {
        return fridgeName;
    }

    public void setFridgeName(String fridgeName) {
        this.fridgeName = fridgeName;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(double minThreshold) {
        this.minThreshold = minThreshold;
    }

    public double getMaxThreshold() {
        return maxThreshold;
    }

    public void setMaxThreshold(double maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
