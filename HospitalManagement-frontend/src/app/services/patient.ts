import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Patient } from '../patient';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  private baseUrl = 'http://localhost:9091';

  constructor(private http: HttpClient) {}

  getAllPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${this.baseUrl}/patient/all`);
  }

  updatePatient(id: number, updatedPatient: Patient): Observable<any> {
    return this.http.put(`${this.baseUrl}/patient/update/${id}`, updatedPatient);
  }

  deletePatient(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/patient/delete/${id}`);
  }

  getPatientsByName(name: string): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${this.baseUrl}/patient/search/${name}`);
  }
}
