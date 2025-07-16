package com.example.demo.service;

import com.example.demo.model.Doctor;
import com.example.demo.model.DoctorCategory;
import com.example.demo.repo.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String addDoctor(Doctor doctor) {
        doctor.setPasswordHash(passwordEncoder.encode(doctor.getPasswordHash()));
        doctorRepo.save(doctor);
        return "Doctor added successfully";
    }
    
    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public String updateDoctor(int id, Doctor updatedDoctor) {
        Optional<Doctor> optional = doctorRepo.findById(id);
        if (optional.isPresent()) {
            Doctor doctor = optional.get();
            doctor.setFullName(updatedDoctor.getFullName());
            doctor.setSpecialty(updatedDoctor.getSpecialty());
            doctor.setExperienceYears(updatedDoctor.getExperienceYears());
            doctor.setQualification(updatedDoctor.getQualification());
            doctor.setDesignation(updatedDoctor.getDesignation());
            doctor.setContactNumber(updatedDoctor.getContactNumber());
            doctor.setEmail(updatedDoctor.getEmail());
            doctor.setPasswordHash(passwordEncoder.encode(updatedDoctor.getPasswordHash()));
            doctorRepo.save(doctor);
            return "Doctor updated successfully!";
        }
        return "Doctor not found!";
    }

    public String deleteDoctor(int id) {
        if (doctorRepo.existsById(id)) {
            doctorRepo.deleteById(id);
            return "Doctor deleted successfully!";
        }
        return "Doctor not found!";
    }

    public List<Doctor> getDoctorsBySpecialty(DoctorCategory specialty) {
        return doctorRepo.findAll().stream()
                .filter(doc -> doc.getSpecialty() == specialty)
                .toList();
    }

    public List<Doctor> searchDoctorByName(String name) {
        return doctorRepo.findAll().stream()
                .filter(doc -> doc.getFullName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }
}
