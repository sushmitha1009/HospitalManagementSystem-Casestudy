package com.example.demo.security;

import com.example.demo.model.Admin;
import com.example.demo.model.Doctor;
import com.example.demo.model.Patient;
import com.example.demo.repo.AdminRepository;
import com.example.demo.repo.DoctorRepository;
import com.example.demo.repo.PatientRepository;
import com.example.demo.repo.DoctorRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (adminRepository.existsByEmail(email)) {
            Admin admin = adminRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
            return new UserPrincipal(admin.getEmail(), admin.getPassword(), "ROLE_ADMIN");  // ✅ updated
        } else if (doctorRepository.existsByEmail(email)) {
            Doctor doctor = doctorRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Doctor not found"));
            return new UserPrincipal(doctor.getEmail(), doctor.getPasswordHash(), "ROLE_DOCTOR");  // ✅ updated
        } else if (patientRepository.existsByEmail(email)) {
            Patient patient = patientRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Patient not found"));
            return new UserPrincipal(patient.getEmail(), patient.getPasswordHash(), "ROLE_PATIENT");  // ✅ updated
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}
