package com.example.haccpbackend.modulTepuratureFrigo.tempurature.device;

import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    boolean existsByDeviceId(String deviceId);

    Optional<Device> findByDeviceId(String deviceId);
    

    List<Device> findByFrigo(Frigo frigo);

    @Query("""
    SELECT d FROM Device d
    JOIN FETCH d.frigo f
""")
    List<Device> findAllWithFrigo();
}
