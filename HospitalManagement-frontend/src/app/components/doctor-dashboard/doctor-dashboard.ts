import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { NgxPaginationModule } from 'ngx-pagination';
import { Appointment } from '../../appointment';
import { Doctor, DoctorCategory } from '../../doctor';
import { Report } from '../../report';
import { DoctorService } from '../../services/doctor';

@Component({
  selector: 'app-doctor-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule,RouterModule, NgxPaginationModule],
  templateUrl: './doctor-dashboard.html',
  styleUrls: ['./doctor-dashboard.scss']
})
export class DoctorDashboard implements OnInit {
  doctors: Doctor[] = [];
  filteredDoctors: Doctor[] = [];

  editingDoctor: Doctor | null = null;
  searchName: string = '';
  specialtyFilter: DoctorCategory | '' = '';
  categories: DoctorCategory[] = [
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

 reportForm: Report = {
  patient: { patientId: 0 },
  doctor: { doctorID: 0 },
  reportType: '',
  description: '',
  date: ''
};

  doctorAppointments: Appointment[] = [];

  // Pagination
  currentPage: number = 1;
  itemsPerPage: number = 5;

  constructor(
    private doctorService: DoctorService,
    private router: Router
  ) {}

  ngOnInit(): void {
  const token = localStorage.getItem('token');
  const role = localStorage.getItem('role');

  if (!token || role !== 'ROLE_DOCTOR') {
    this.router.navigate(['/doctor-login']);
    return;
  }

  this.fetchDoctors();
}

  fetchDoctors(): void {
    this.doctorService.getAllDoctors().subscribe({
      next: (res) => {
        this.doctors = res;
        this.filteredDoctors = res;
      },
      error: (err) => {
        console.error('Error fetching doctors:', err);
        alert('Failed to load doctors. Please check your login or server.');
      }
    });
  }

  deleteDoctor(id: number | undefined): void {
    if (!id) return;
    if (confirm('Are you sure you want to delete this doctor?')) {
      this.doctorService.deleteDoctor(id).subscribe(() => {
        alert('Doctor deleted successfully!');
        this.fetchDoctors();
      });
    }
  }

  editDoctor(doctor: Doctor): void {
    this.editingDoctor = { ...doctor };
  }

  updateDoctor(): void {
    if (!this.editingDoctor || !this.editingDoctor.doctorID) return;
    this.doctorService.updateDoctor(this.editingDoctor.doctorID, this.editingDoctor).subscribe(() => {
      alert('Doctor updated successfully!');
      this.editingDoctor = null;
      this.fetchDoctors();
    });
  }

  cancelEdit(): void {
    this.editingDoctor = null;
  }

  searchByName(): void {
    if (!this.searchName.trim()) {
      this.filteredDoctors = this.doctors;
      return;
    }
    this.doctorService.searchDoctorByName(this.searchName).subscribe((res) => {
      this.filteredDoctors = res;
    });
  }

  filterBySpecialty(): void {
    if (!this.specialtyFilter) {
      this.filteredDoctors = this.doctors;
      return;
    }
    this.doctorService.getDoctorsBySpecialty(this.specialtyFilter as DoctorCategory).subscribe((res) => {
      this.filteredDoctors = res;
    });
  }

  clearFilters(): void {
    this.searchName = '';
    this.specialtyFilter = '';
    this.filteredDoctors = this.doctors;
    this.currentPage = 1;
  }

  uploadReport(): void {
  const storedDoctorId = localStorage.getItem('doctorID');
  this.reportForm.doctor.doctorID = storedDoctorId ? parseInt(storedDoctorId, 10) : 0;
  this.reportForm.date = new Date().toISOString().split('T')[0]; // Only yyyy-mm-dd format

  console.log('Submitting report:', this.reportForm);

  this.doctorService.uploadReport(this.reportForm).subscribe({
    next: () => {
      alert('Report uploaded successfully!');
      this.reportForm = {
        reportType: '',
        description: '',
        date: '',
        doctor: {
          doctorID: 0
        },
        patient: {
          patientId: 0
        }
      };
    },
    error: (err) => {
      console.error('Error uploading report:', err);
      alert('Failed to upload report.');
    }
  });
}

  viewAppointments(doctorId: number): void {
    this.doctorService.getAppointmentsByDoctorId(doctorId).subscribe((appointments) => {
      this.doctorAppointments = appointments;
    });
  }

  logout(): void {
  // Clear all saved auth/session data
  localStorage.clear();

  // Redirect to home page and prevent going back
  window.location.replace('/');
}

}
