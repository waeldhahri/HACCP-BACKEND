package com.example.haccpbackend.modulTepuratureFrigo.tempurature.TemperatureFrigoService2;




public class TemperatureRequest {
    private Long frigoId;
    private Double temperature;
    private Integer batteryLevel;

    public Long getFrigoId() {
        return frigoId;
    }

    public void setFrigoId(Long frigoId) {
        this.frigoId = frigoId;
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
}
