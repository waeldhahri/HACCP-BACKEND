package com.example.haccpbackend.modulTepuratureFrigo.tempurature.device;

import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
import com.example.haccpbackend.modulTepuratureFrigo.FrigoLightDTO;
import com.example.haccpbackend.modulTepuratureFrigo.FrigoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final FrigoRepository frigoRepository;

    public DeviceService(DeviceRepository deviceRepository, FrigoRepository frigoRepository) {
        this.deviceRepository = deviceRepository;
        this.frigoRepository = frigoRepository;
    }

    public Device createDevice(DeviceDto request) {
        // 🔒 Vérifier si l'ID du device est déjà utilisé
        if (deviceRepository.existsByDeviceId(request.getDeviceId())) {
            throw new RuntimeException("❌ Ce deviceId existe déjà !");
        }

        Device device = new Device();
        device.setDeviceId(request.getDeviceId());
        device.setDescription(request.getDescription());

        // ✅ Associer à un frigo si frigoId est fourni
        if (request.getFrigoId() != null) {
            Frigo frigo = frigoRepository.findById(request.getFrigoId())
                    .orElseThrow(() -> new RuntimeException("Frigo introuvable !"));
            device.setFrigo(frigo);
        }

        return deviceRepository.save(device);
    }


    @Transactional(readOnly = true)
    public List<DeviceDTO2> getAllDevices() {

        return deviceRepository.findAllWithFrigo()
                .stream()
                .map(d -> {
                    DeviceDTO2 dto = new DeviceDTO2();
                    dto.setId(d.getId());
                    dto.setDeviceId(d.getDeviceId());
                    dto.setDescription(d.getDescription());

                    Frigo f = d.getFrigo();
                    if (f != null) {
                        FrigoLightDTO frigoDTO = new FrigoLightDTO();
                        frigoDTO.setId(f.getId());
                        frigoDTO.setName(f.getName());
                        frigoDTO.setActive(f.isActive());
                        frigoDTO.setImageUrl(f.getImageUrl());

                        dto.setFrigo(frigoDTO);
                    }

                    return dto;
                })
                .toList();
    }

}
