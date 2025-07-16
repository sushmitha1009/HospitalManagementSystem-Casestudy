package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Doctor;
import com.example.demo.model.Patient;
import com.example.demo.model.Report;
import com.example.demo.repo.DoctorRepository;
import com.example.demo.repo.PatientRepository;
import com.example.demo.repo.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository repo;

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    public String uploadReport(Report report) {
        // Ensure doctor and patient exist and are attached as full objects
        Doctor doctor = doctorRepo.findById(report.getDoctor().getDoctorID())
            .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Patient patient = patientRepo.findById(report.getPatient().getPatientId())
            .orElseThrow(() -> new RuntimeException("Patient not found"));

        report.setDoctor(doctor);
        report.setPatient(patient);

        repo.save(report);
        return "Report uploaded!";
    }


    public List<Report> getByPatientId(int patientId) {
        return repo.findByPatient_PatientId(patientId);
    }
}
