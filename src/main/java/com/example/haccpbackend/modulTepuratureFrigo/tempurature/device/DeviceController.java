package com.example.haccpbackend.modulTepuratureFrigo.tempurature.device;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@CrossOrigin
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    // ➕ Ajouter un nouveau Device
    @PostMapping
    public Device addDevice(@RequestBody DeviceDto request) {
        return deviceService.createDevice(request);
    }
}
