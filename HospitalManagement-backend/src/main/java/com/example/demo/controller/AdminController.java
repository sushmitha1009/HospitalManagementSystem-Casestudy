package com.example.demo.controller;

import com.example.demo.model.Admin;
import com.example.demo.model.Doctor;
import com.example.demo.model.Patient;
import com.example.demo.service.AdminService;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addAdmin(@RequestBody Admin admin) {
        Map<String, String> response = new HashMap<>();
        response.put("message", adminService.addAdmin(admin));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> updateAdmin(@PathVariable int id, @RequestBody Admin updatedAdmin) {
        String message = adminService.updateAdmin(id, updatedAdmin);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable int id) {
        return ResponseEntity.ok(adminService.deleteAdmin(id));
    }

    // ✅ Add Doctor from Admin panel
    @PostMapping("/add-doctor")
    public ResponseEntity<Map<String, String>> addDoctor(@RequestBody Doctor doctor) {
        doctorService.addDoctor(doctor);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Doctor added successfully by Admin");
        return ResponseEntity.ok(response);
    }

    // ✅ Add Patient from Admin panel
    @PostMapping("/add-patient")
    public ResponseEntity<Map<String, String>> addPatient(@RequestBody Patient patient) {
        patientService.addPatient(patient);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Patient added successfully by Admin");
        return ResponseEntity.ok(response);
    }
}
