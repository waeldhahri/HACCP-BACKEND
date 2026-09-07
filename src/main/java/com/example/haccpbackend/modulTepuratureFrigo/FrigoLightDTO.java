package com.example.haccpbackend.modulTepuratureFrigo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrigoLightDTO {
    private Long id;
    private String name;
    private boolean active;
    private String imageUrl;
}