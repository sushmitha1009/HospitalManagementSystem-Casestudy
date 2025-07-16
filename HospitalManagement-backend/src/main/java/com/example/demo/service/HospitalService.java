package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Hospital;
import com.example.demo.repo.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public String addHospital(Hospital hospital) {
        hospitalRepository.save(hospital);
        return "Hospital Added Successfully";
    }

    public List<Hospital> findByCity(String city) {
        List<Hospital> hospitals = hospitalRepository.findByCity(city);
        if (hospitals.isEmpty()) {
            throw new ResourceNotFoundException("No hospitals found in city: " + city);
        }
        return hospitals;
    }

    public List<Hospital> findByCityAndLocation(String city, String location) {
        List<Hospital> hospitals = hospitalRepository.findByCityAndLocation(city, location);
        if (hospitals.isEmpty()) {
            throw new ResourceNotFoundException("No hospitals found in city: " + city + " and location: " + location);
        }
        return hospitals;
    }
}