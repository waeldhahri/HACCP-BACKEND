package com.example.haccpbackend.modulTepuratureFrigo.tempurature.device;


import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
@CrossOrigin
public class DeviceController {

    private final DeviceService deviceService;
    private final DeviceRepository deviceRepository;

    public DeviceController(DeviceService deviceService, DeviceRepository deviceRepository) {
        this.deviceService = deviceService;
        this.deviceRepository = deviceRepository;
    }

    // ➕ Ajouter un nouveau Device
    @PostMapping("/addNewDevice")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public Device addDevice(@RequestBody DeviceDto request) {
        return deviceService.createDevice(request);
    }



/*
    // ➕ All Devices
    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public List<Device> getallDevices() {


        return deviceRepository.findAll();

    }
*/

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public List<DeviceDTO2> getallDevices2() {
        return deviceService.getAllDevices();
    }
}
