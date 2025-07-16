import { Routes } from '@angular/router';
import { AdminGuard } from './guards/admin.guard';
import { DoctorGuard } from './guards/doctor.guard';
import { PatientGuard } from './guards/patient.guard';

export const routes: Routes = [
  { path: '', loadComponent: () => import('./components/home/home').then(m => m.Home) },

  { path: 'admin-login', loadComponent: () => import('./components/admin-login/admin-login').then(m => m.AdminLogin) },
  { path: 'doctor-login', loadComponent: () => import('./components/doctor-login/doctor-login').then(m => m.DoctorLogin) },
  { path: 'patient-login', loadComponent: () => import('./components/patient-login/patient-login').then(m => m.PatientLogin) },

  { path: 'admin-dashboard', canActivate: [AdminGuard], loadComponent: () => import('./components/admin-dashboard/admin-dashboard').then(m => m.AdminDashboard) },
  { path: 'doctor-dashboard', canActivate: [DoctorGuard], loadComponent: () => import('./components/doctor-dashboard/doctor-dashboard').then(m => m.DoctorDashboard) },
  { path: 'patient-dashboard', canActivate: [PatientGuard], loadComponent: () => import('./components/patient-dashboard/patient-dashboard').then(m => m.PatientDashboard) },

  { path: 'register-admin', loadComponent: () => import('./register-admin/register-admin').then(m => m.RegisterAdmin) },
  { path: 'register-doctor', loadComponent: () => import('./register-doctor/register-doctor').then(m => m.RegisterDoctor) },
  { path: 'register-patient', loadComponent: () => import('./register-patient/register-patient').then(m => m.RegisterPatient) },
];
