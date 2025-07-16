package com.example.demo.controller;

import com.example.demo.model.Doctor;
import com.example.demo.model.Hospital;
import com.example.demo.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/addHospital")
    public ResponseEntity<String> addHospital(@RequestBody Hospital hospital) {
        return (ResponseEntity<String>) ResponseEntity.ok(hospitalService.addHospital(hospital));
    }
   
    @GetMapping("/city/{city}")
    public ResponseEntity<List<Hospital>> findByCity(@PathVariable String city) {
        return ResponseEntity.ok(hospitalService.findByCity(city));
    }

    @GetMapping("/city/{city}/location/{location}")
    public ResponseEntity<List<Hospital>> findByCityAndLocation(@PathVariable String city, @PathVariable String location) {
        return ResponseEntity.ok(hospitalService.findByCityAndLocation(city, location));
    }
}