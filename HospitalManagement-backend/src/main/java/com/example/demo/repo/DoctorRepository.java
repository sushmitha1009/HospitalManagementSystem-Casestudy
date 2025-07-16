
package com.example.demo.repo;

import com.example.demo.model.Doctor;
import com.example.demo.model.DoctorCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Optional<Doctor> findByEmail(String email);
    boolean existsByEmail(String email);
}