package com.example.demo.service;

import com.example.demo.model.Admin;
import com.example.demo.repo.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String addAdmin(Admin admin) {
        // ✅ Hash the password
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepo.save(admin);
        return "Admin added successfully";
    }

    public List<Admin> getAllAdmins() {
        return adminRepo.findAll();
    }

    public String updateAdmin(int id, Admin updatedAdmin) {
        Optional<Admin> optional = adminRepo.findById(id);
        if (optional.isPresent()) {
            Admin admin = optional.get();
            admin.setName(updatedAdmin.getName());
            admin.setEmail(updatedAdmin.getEmail());
            admin.setPassword(updatedAdmin.getPassword());
            adminRepo.save(admin);
            return "Admin updated successfully!";
        }
        return "Admin not found!";
    }

    public String deleteAdmin(int id) {
        if (adminRepo.existsById(id)) {
            adminRepo.deleteById(id);
            return "Admin deleted successfully!";
        }
        return "Admin not found!";
    }
}
