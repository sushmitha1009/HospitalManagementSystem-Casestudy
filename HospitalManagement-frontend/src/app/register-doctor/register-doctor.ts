import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-doctor',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register-doctor.html',
  styleUrls: ['./register-doctor.scss']
})
export class RegisterDoctor {
  doctor = {
    fullName: '',
    specialty: '',
    experience_years: 0,
    qualification: '',
    designation: '',
    contactNumber: '',
    email: '',
    passwordHash: ''
  };

  specialties: string[] = [
    'Cardiology',
    'Orthopedics',
    'Pediatrics',
    'Neurology',
    'Dermatology',
    'GeneralMedicine',
    'Anesthesia',
    'Psychiatry',
    'ENT'
  ];

  constructor(private http: HttpClient, private router: Router) {}

  register() {
    console.log("Doctor data before submission:", this.doctor);
    this.http.post('http://localhost:9091/doctor/add', this.doctor)
      .subscribe(() => {
        alert('Doctor Registered Successfully');
        this.router.navigate(['/doctor-login']);
      });
  }
}
