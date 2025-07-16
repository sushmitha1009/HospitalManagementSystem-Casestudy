import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Appointment } from '../appointment';
import { Doctor, DoctorCategory } from '../doctor';
import { Report } from '../report';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {
  private baseUrl = 'http://localhost:9091';

  constructor(private http: HttpClient) {}

  getAllDoctors(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(`${this.baseUrl}/doctor/all`);
  }

  updateDoctor(id: number, doctor: Doctor): Observable<any> {
    return this.http.put(`${this.baseUrl}/doctor/update/${id}`, doctor);
  }

  deleteDoctor(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/doctor/delete/${id}`);
  }

  getDoctorsBySpecialty(specialty: DoctorCategory): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(`${this.baseUrl}/doctor/specialty/${specialty}`);
  }

  searchDoctorByName(name: string): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(`${this.baseUrl}/doctor/search/${name}`);
  }

  uploadReport(report: Report): Observable<any> {
    return this.http.post(`${this.baseUrl}/report/upload`, report);
  }

  getAppointmentsByDoctorId(doctorId: number): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.baseUrl}/appointment/doctor/${doctorId}`);
  }
}
