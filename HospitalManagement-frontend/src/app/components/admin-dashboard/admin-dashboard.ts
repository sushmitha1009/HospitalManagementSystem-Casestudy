import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule,RouterModule],
  templateUrl: './admin-dashboard.html',
  styleUrls: ['./admin-dashboard.scss']
})
export class AdminDashboard implements OnInit {
  admins: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.getAllAdmins();
  }

  getAllAdmins() {
    this.http.get<any[]>('http://localhost:9091/admin/all').subscribe({
      next: (data) => {
        console.log('Fetched admins:', data);
        this.admins = data;
      },
      error: (err) => {
        console.error('Failed to fetch admins', err);
        alert('Error fetching admin data');
      }
    });
  }

  deleteAdmin(id: number) {
    if (confirm('Are you sure you want to delete this admin?')) {
      this.http.delete(`http://localhost:9091/admin/delete/${id}`, { responseType: 'text' }).subscribe({
  next: () => {
    alert('Admin deleted successfully!');
    this.getAllAdmins();
  },
  error: (err) => {
    console.error('Failed to delete admin', err);
    alert('Failed to delete admin');
  }
});
    }
  }

 updateAdmin(admin: any) {
  const updatedEmail = prompt('Enter new email', admin.email);
  if (updatedEmail) {
    const updatedAdmin = { ...admin, email: updatedEmail };
    this.http.put(`http://localhost:9091/admin/update/${admin.id}`, updatedAdmin, {
      headers: { 'Content-Type': 'application/json' }
    }).subscribe({
      next: (response: any) => {
        alert(response.message || 'Admin updated successfully!');
        this.getAllAdmins();
      },
      error: (err) => {
        console.error('Update failed:', err);
        alert('Failed to update admin');
      }
    });
  }
}

logout(): void {
  // Clear all saved auth/session data
  localStorage.clear();

  // Redirect to home page and prevent going back
  window.location.replace('/');
}
}
