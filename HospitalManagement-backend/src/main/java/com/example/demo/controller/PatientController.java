package com.example.demo.controller;

import com.example.demo.model.Patient;

import com.example.demo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/patient")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addPatient(@RequestBody Patient patient) {
        Map<String, String> response = new HashMap<>();
        response.put("message", patientService.addPatient(patient));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public List<Patient> showPatient() {
        return patientService.getAllPatients();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> updatePatient(@PathVariable int id, @RequestBody Patient updatedPatient) {
        return patientService.updatePatient(id, updatedPatient);
    }
    
    @GetMapping("/search/{name}")
    public ResponseEntity<List<Patient>> getPatientsByName(@PathVariable String name) {
        return ResponseEntity.ok(patientService.getPatientsByName(name));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable int id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok("Patient deleted successfully.");
    }

}