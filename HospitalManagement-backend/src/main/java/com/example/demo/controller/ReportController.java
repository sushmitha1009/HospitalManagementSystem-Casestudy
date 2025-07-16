package com.example.demo.controller;

import com.example.demo.model.Report;
import com.example.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/report")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportController {

    @Autowired
    private ReportService service;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> upload(@RequestBody Report report) {
        String message = service.uploadReport(report);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAuthority('ROLE_PATIENT')")
    @GetMapping("/get/patient/{id}")
    public ResponseEntity<List<Report>> getByPatient(@PathVariable int id) {
        return ResponseEntity.ok(service.getByPatientId(id));
    }
}
