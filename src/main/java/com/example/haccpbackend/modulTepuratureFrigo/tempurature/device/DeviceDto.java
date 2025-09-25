package com.example.haccpbackend.modulTepuratureFrigo.tempurature.device;

public class DeviceDto {


    private String deviceId;
    private String description;
    private Long  frigoId;

    //Constructors
    public DeviceDto(String deviceId, String description, Long frigoId) {
        this.deviceId = deviceId;
        this.description = description;
        this.frigoId = frigoId;
    }

    public DeviceDto() {
    }


    // Getters and Setters


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFrigoId() {
        return frigoId;
    }

    public void setFrigoId(Long frigoId) {
        this.frigoId = frigoId;
    }
}
