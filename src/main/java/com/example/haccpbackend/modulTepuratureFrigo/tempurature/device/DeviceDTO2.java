package com.example.haccpbackend.modulTepuratureFrigo.tempurature.device;

import com.example.haccpbackend.modulTepuratureFrigo.FrigoLightDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeviceDTO2 {


    private Long id;
    private String deviceId;
    private String description;
    private FrigoLightDTO frigo;
}
