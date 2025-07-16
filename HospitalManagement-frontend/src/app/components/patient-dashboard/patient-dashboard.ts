// src/app/components/patient-dashboard/patient-dashboard.ts

import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Appointment } from '../../appointment';
import { Doctor } from '../../doctor';
import { Patient } from '../../patient';
import { Report } from '../../report';
import { AppointmentService } from '../../services/appointment';
import { DoctorService } from '../../services/doctor';
import { PatientService } from '../../services/patient';
import { ReportService } from '../../services/report';

@Component({
  selector: 'app-patient-dashboard',
  templateUrl: './patient-dashboard.html',
  styleUrls: ['./patient-dashboard.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule]
})
export class PatientDashboard implements OnInit {
  patient: Patient | null = null;
  showPassword: boolean = false;

  newAppointment: Appointment = {
    patientId: 0,
    doctorID: 0,
    appointmentDate: '',
    reason: '',
    status: 'Pending'
  };

  doctors: Doctor[] = [];
  appointments: Appointment[] = [];
  matchedPatients: Patient[] = [];
  searchName: string = '';
  reports: Report[] = [];

  constructor(
    private patientService: PatientService,
    private appointmentService: AppointmentService,
    private doctorService: DoctorService,
    private reportService: ReportService,
    private router: Router
  ) {}

  ngOnInit(): void {
    console.log('Checking localStorage:', localStorage);

    this.loadLoggedInPatient();
    this.fetchDoctors();
  }

  loadLoggedInPatient(): void {
    const stored = localStorage.getItem('loggedInPatient');
    if (stored) {
      this.patient = JSON.parse(stored);
      this.newAppointment.patientId = this.patient.patientId;
      this.fetchAppointments();
      this.fetchReports();
    } else {
      console.warn('No patient info in localStorage');
      this.router.navigate(['/patient-login']);
    }
  }

  fetchReports(): void {
    if (!this.patient?.patientId) return;

    this.reportService.getReportsByPatient(this.patient.patientId).subscribe({
      next: (res) => {
        this.reports = res;
      },
      error: (err) => {
        console.error('Error fetching reports:', err);
      }
    });
  }

  updateProfile(): void {
    const payload = { ...this.patient };

    if (!payload.passwordHash || payload.passwordHash.trim() === '') {
      delete payload.passwordHash;
    }

    console.log('Updating profile for ID:', payload.patientId);
    console.log('Payload:', payload);

    this.patientService.updatePatient(payload.patientId, payload).subscribe({
      next: () => {
        alert('Profile updated successfully!');
        localStorage.setItem('loggedInPatient', JSON.stringify(this.patient));
      },
      error: (err) => {
        console.error('Error during profile update:', err);
      }
    });
  }

  deleteProfile(): void {
    if (!this.patient || !this.patient.patientId) return;

    if (confirm('Are you sure you want to delete your profile?')) {
      this.patientService.deletePatient(this.patient.patientId).subscribe(() => {
        alert('Profile deleted!');
        localStorage.clear();
        this.router.navigate(['/']);
      });
    }
  }

  fetchDoctors(): void {
    this.doctorService.getAllDoctors().subscribe((res) => {
      this.doctors = res;
    });
  }

  bookAppointment(): void {
    if (!this.patient) return;

    const dateStr = this.newAppointment.appointmentDate;
    const isoDate = `${dateStr}:00`;

    const payload = {
      ...this.newAppointment,
      patientId: this.patient.patientId,
      appointmentDate: isoDate
    };

    this.appointmentService.bookAppointment(payload).subscribe(() => {
      alert('Appointment booked successfully!');
      this.newAppointment = {
        patientId: this.patient!.patientId,
        doctorID: 0,
        appointmentDate: '',
        reason: '',
        status: 'Pending'
      };
      this.fetchAppointments();
    });
  }

  fetchAppointments(): void {
    if (!this.patient?.patientId) return;

    this.appointmentService.getAppointmentsByPatient(this.patient.patientId).subscribe((res) => {
      this.appointments = res;
    });
  }

  searchPatients(): void {
    if (this.searchName.trim() === '') {
      this.matchedPatients = [];
      return;
    }

    this.patientService.getPatientsByName(this.searchName).subscribe((res) => {
      this.matchedPatients = res;
    });
  }

  logout(): void {
  // Clear all saved auth/session data
  localStorage.clear();

  // Redirect to home page and prevent going back
  window.location.replace('/');
}

}
