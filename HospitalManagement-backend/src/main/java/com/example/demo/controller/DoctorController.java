package com.example.demo.controller;

import com.example.demo.model.Doctor;
import com.example.demo.model.DoctorCategory;
import com.example.demo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctor")
@CrossOrigin(origins = "http://localhost:4200")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addDoctor(@RequestBody Doctor doctor) {
        Map<String, String> response = new HashMap<>();
        response.put("message",doctorService.addDoctor(doctor));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public List<Doctor> showDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialty(@PathVariable DoctorCategory specialty) {
        return ResponseEntity.ok(doctorService.getDoctorsBySpecialty(specialty));
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<Doctor>> searchDoctorByName(@PathVariable String name) {
        return ResponseEntity.ok(doctorService.searchDoctorByName(name));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDoctor(@PathVariable int id, @RequestBody Doctor doctor) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctor));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable int id) {
        return ResponseEntity.ok(doctorService.deleteDoctor(id));
    }
}