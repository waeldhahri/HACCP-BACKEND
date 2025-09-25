package com.example.haccpbackend.modulTepuratureFrigo.tempurature.device;

import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
import com.example.haccpbackend.modulTepuratureFrigo.FrigoRepository;
import org.springframework.stereotype.Service;

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
}
