package com.example.demo.repo;

import com.example.demo.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {
	List<Report> findByPatient_PatientId(int patientId); // ✅ exact field name  // <-- use underscore to go through object property
}
