// src/app/components/patient-login/patient-login.ts

import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-patient-login',
  standalone: true,
  templateUrl: './patient-login.html',
  imports: [CommonModule, ReactiveFormsModule, FormsModule, RouterModule]
})
export class PatientLogin {
  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: [''],
      password: ['']
    });
  }

  loginPatient() {
  this.authService.loginAsPatient(this.loginForm.value).subscribe({
    next: (res: any) => {
      console.log('Login response:', res);

      // ✅ Save token and role
      this.authService.setSession(res.token, res.role);

      // ✅ Save entire patient info
      if (res.patientId) {
        localStorage.setItem('patientId', res.patientId.toString());
        localStorage.setItem('loggedInPatient', JSON.stringify(res));
        console.log('Saved patientId:', res.patientId);
      }

      // ✅ Add small timeout to allow routing to process
      setTimeout(() => {
        this.router.navigateByUrl('/patient-dashboard');
      }, 100);
    },
    error: (err) => {
      console.error('Login failed:', err);
      alert('Login failed. Check credentials.');
    }
  });
}
}
