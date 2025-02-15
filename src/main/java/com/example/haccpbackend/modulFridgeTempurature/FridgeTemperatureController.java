package com.example.haccpbackend.modulFridgeTempurature;


import com.example.haccpbackend.modulFridgeTempurature.FridgeTemperature;
import com.example.haccpbackend.modulFridgeTempurature.FridgeTemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fridges")
public class FridgeTemperatureController {


    @Autowired
    private FridgeTemperatureService service;



    @PostMapping("/record")
    public ResponseEntity<FridgeTemperature> recordTemperature(@RequestParam String fridgeName, @RequestParam double temperature) {
        return ResponseEntity.ok(service.saveTemperature(fridgeName, temperature));
    }



}
