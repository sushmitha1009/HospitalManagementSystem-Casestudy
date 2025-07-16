package com.example.demo.service;

import com.example.demo.model.Appointment;
import com.example.demo.repo.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository repo;

    public String bookAppointment(Appointment appt) {
        repo.save(appt);
        return "Appointment booked successfully!";
    }

    public List<Appointment> getByPatientId(int id) {
        return repo.findByPatientId(id);
    }

    public List<Appointment> getByDoctorID(int id) {
        return repo.findByDoctorID(id);
    }
}
