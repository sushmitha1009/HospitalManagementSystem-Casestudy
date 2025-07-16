// src/app/services/report.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Report } from '../report';

@Injectable({ providedIn: 'root' })
export class ReportService {
  private baseUrl = 'http://localhost:9091/report';

  constructor(private http: HttpClient) {}

  getReportsByPatient(patientId: number): Observable<Report[]> {
    return this.http.get<Report[]>(`${this.baseUrl}/get/patient/${patientId}`);
  }
}
