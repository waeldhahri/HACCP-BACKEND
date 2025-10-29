package com.example.haccpbackend.modulTepuratureFrigo.tempurature.device;


import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
@CrossOrigin
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    // ➕ Ajouter un nouveau Device
    @PostMapping("/addNewDevice")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public Device addDevice(@RequestBody DeviceDto request) {
        return deviceService.createDevice(request);
    }
}
