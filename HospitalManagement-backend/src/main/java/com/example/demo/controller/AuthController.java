package com.example.demo.controller;

import com.example.demo.config.JwtService;
import com.example.demo.dto.PatientAuthRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Admin;
import com.example.demo.model.Doctor;
import com.example.demo.model.Patient;
import com.example.demo.repo.AdminRepository;
import com.example.demo.repo.DoctorRepository;
import com.example.demo.repo.PatientRepository;
import com.example.demo.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private PatientService patientService;

    // Admin login
    @PostMapping("/login/admin")
    public ResponseEntity<?> loginAdmin(@RequestBody PatientAuthRequest authRequest) {
        Admin admin = adminRepo.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
        if (!passwordEncoder.matches(authRequest.getPassword(), admin.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        String token = jwtService.generateToken(admin.getEmail(), "ADMIN");
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", "admin"); // ✅ include role
        return ResponseEntity.ok(response);
    }

 // Doctor login
    @PostMapping("/login/doctor")
    public ResponseEntity<?> loginDoctor(@RequestBody PatientAuthRequest authRequest) {
        Doctor doctor = doctorRepo.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        if (!passwordEncoder.matches(authRequest.getPassword(), doctor.getPasswordHash())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        String token = jwtService.generateToken(doctor.getEmail(), "DOCTOR");
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", "doctor");
        response.put("doctorID", doctor.getDoctorID()); // ✅ send as Integer
        return ResponseEntity.ok(response);
    }


    @PostMapping("/login/patient")
    public ResponseEntity<?> loginPatient(@RequestBody PatientAuthRequest request) {
        try {
            // Authenticate patient
            Patient patient = patientService.login(request.getEmail(), request.getPassword());

            // Generate JWT
            String token = jwtService.generateToken(patient.getEmail(), "PATIENT");

            // Prepare response
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", "PATIENT");
            response.put("patientId", patient.getPatientId());
            response.put("fullName", patient.getFullName());
            response.put("email", patient.getEmail());
            response.put("gender", patient.getGender());
            response.put("contactNumber", patient.getContactNumber());
            response.put("medicalHistory", patient.getMedicalHistory());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}

