import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

interface AuthResponse {
  token: string;
  role: string;
  message?: string; // <-- Add this to support backend message
}

@Component({
  selector: 'app-admin-login',
  standalone: true,
  templateUrl: './admin-login.html',
  imports: [CommonModule, ReactiveFormsModule, FormsModule, RouterModule]
})
export class AdminLogin {
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

  login() {
    this.authService.loginAsAdmin(this.loginForm.value).subscribe({
      next: (res: AuthResponse) => {
        if (res.token && res.role) {
          localStorage.setItem('token', res.token);
          this.authService.setSession(res.token, res.role);
          alert(res.message || 'Login successful!');
          setTimeout(() => {
            this.router.navigate(['/admin-dashboard']);
          }, 200); // Delay ensures guard reads correct role
        } else {
          alert(res.message || 'Login failed!');
        }
      },
      error: (err) => {
        console.error('Login error:', err);
        alert('Login failed. Check credentials.');
      }
    });
  }
}
