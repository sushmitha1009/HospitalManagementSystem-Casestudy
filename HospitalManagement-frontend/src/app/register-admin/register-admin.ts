import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-admin',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register-admin.html',
  styleUrls: ['./register-admin.scss']
})
export class RegisterAdmin {
  admin = {
    name: '',
    email: '',
    password: ''
  };

  constructor(private http: HttpClient, private router: Router) {}

  register() {
  this.http.post<{ message: string }>('http://localhost:9091/admin/add', this.admin)
    .subscribe({
      next: (res) => {
        alert(res.message || 'Admin Registered Successfully!');
        this.router.navigate(['/admin-login']);
      },
      error: (err) => {
        console.error('Registration failed:', err);
        alert('Failed to register admin.');
      }
    });
}
}
