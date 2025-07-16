package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Patient;
import com.example.demo.repo.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String addPatient(Patient patient) {
        patient.setPasswordHash(passwordEncoder.encode(patient.getPasswordHash()));
        patientRepo.save(patient);
        return "Patient added successfully";
    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    public ResponseEntity<Map<String, String>> updatePatient(int id, Patient updatedPatient) {
        Optional<Patient> optional = patientRepo.findById(id);
        Map<String, String> response = new HashMap<>();

        if (optional.isPresent()) {
            Patient patient = optional.get();

            // Basic field updates
            patient.setFullName(updatedPatient.getFullName());
            patient.setDateOfBirth(updatedPatient.getDateOfBirth());
            patient.setGender(updatedPatient.getGender());
            patient.setContactNumber(updatedPatient.getContactNumber());
            patient.setEmail(updatedPatient.getEmail());
            patient.setMedicalHistory(updatedPatient.getMedicalHistory());

            // Only update password if user entered one
            String newPassword = updatedPatient.getPasswordHash();
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                patient.setPasswordHash(passwordEncoder.encode(newPassword));
            }

            // Preserve registrationDate from original (do not update)
            // patient.setRegistrationDate(patient.getRegistrationDate()); ← no need, it’s already retained

            patientRepo.save(patient);
            response.put("message", "Patient updated successfully!");
            return ResponseEntity.ok(response);
        }

        response.put("message", "Patient not found!");
        return ResponseEntity.status(404).body(response);
    }

    public String deletePatient(int id) {
        if (patientRepo.existsById(id)) {
            patientRepo.deleteById(id);
            return "Patient deleted successfully!";
        }
        return "Patient not found!";
    }

    public List<Patient> getPatientsByName(String name) {
        return patientRepo.findAll().stream()
                .filter(p -> p.getFullName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    public List<Patient> showPatient() {
        return patientRepo.findAll();
    }

    public Patient login(String email, String password) {
        Patient patient = patientRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email"));

        if (!passwordEncoder.matches(password, patient.getPasswordHash())) {
            throw new ResourceNotFoundException("Invalid password");
        }

        return patient;
    }
}
