package com.example.haccpbackend.modulTepuratureFrigo.tempurature;

import java.time.LocalDateTime;

public class TemperatureFrigoDTO {
    private Long id;
    private Double temperature;
    private Integer batteryLevel;
    private LocalDateTime datetime;
    private String frigoName;
    private String deviceName;

    public TemperatureFrigoDTO(Long id, Double temperature, Integer batteryLevel,
                               LocalDateTime datetime, String frigoName, String deviceName) {
        this.id = id;
        this.temperature = temperature;
        this.batteryLevel = batteryLevel;
        this.datetime = datetime;
        this.frigoName = frigoName;
        this.deviceName = deviceName;
    }

    public TemperatureFrigoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getFrigoName() {
        return frigoName;
    }

    public void setFrigoName(String frigoName) {
        this.frigoName = frigoName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
