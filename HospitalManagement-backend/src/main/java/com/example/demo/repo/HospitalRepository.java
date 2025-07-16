package com.example.demo.repo;

import com.example.demo.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {
    List<Hospital> findByCity(String city);
    List<Hospital> findByCityAndLocation(String city, String location);
}