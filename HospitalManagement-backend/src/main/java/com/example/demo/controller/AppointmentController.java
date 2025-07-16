package com.example.demo.controller;

import com.example.demo.model.Appointment;
import com.example.demo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/appointment")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @PostMapping("/book")
    public ResponseEntity<Map<String, String>> bookAppointment(@RequestBody Appointment appointment) {
        service.bookAppointment(appointment);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Appointment booked successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<Appointment>> getByPatient(@PathVariable int id) {
        return ResponseEntity.ok(service.getByPatientId(id));
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<Appointment>> getByDoctor(@PathVariable int id) {
        return ResponseEntity.ok(service.getByDoctorID(id));
    }
}
