import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthResponse } from '../../models/auth-response'; // ✅ Import the type
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-doctor-login',
  standalone: true,
  templateUrl: './doctor-login.html',
  imports: [CommonModule, ReactiveFormsModule, FormsModule, RouterModule]
})
export class DoctorLogin {
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

  // doctor-login.ts
loginDoctor() {
  this.authService.loginAsDoctor(this.loginForm.value).subscribe({
    next: (res: AuthResponse) => {
      console.log('Login response:', res);

      // Force role format
      this.authService.setSession(res.token, 'ROLE_DOCTOR');

      if (res.doctorID) {
        localStorage.setItem('doctorID', res.doctorID.toString());
      }

      this.router.navigate(['/doctor-dashboard']);
    },
    error: (err) => {
      console.error('Login failed:', err);
      alert('Login failed. Check credentials.');
    }
  });
}
}
