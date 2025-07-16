import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-patient',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register-patient.html',
  styleUrls: ['./register-patient.scss']
})
export class RegisterPatient {
  genderOptions = ['Male', 'Female', 'Other'];
  todayDate = new Date().toISOString().split('T')[0];

  patient = {
    fullName: '',
    dateOfBirth: '',
    gender: '',
    contactNumber: '',
    email: '',
    passwordHash: '',
    medicalHistory: '',
    registrationDate: this.todayDate
  };

  constructor(private http: HttpClient, private router: Router) {}

  register() {
    this.http.post('http://localhost:9091/patient/add', this.patient).subscribe(() => {
      alert('Patient Registered Successfully');
      this.router.navigate(['/patient-login']);
    });
  }
}
