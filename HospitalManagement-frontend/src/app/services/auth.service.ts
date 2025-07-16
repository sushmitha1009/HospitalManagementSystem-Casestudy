// src/app/services/auth.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthResponse } from '../models/auth-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:9091';

  constructor(private http: HttpClient) {}

  loginAsAdmin(credentials: any): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/auth/login/admin`, credentials);
  }

  loginAsDoctor(credentials: any): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/auth/login/doctor`, credentials);
  }

  loginAsPatient(credentials: any): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/auth/login/patient`, credentials);
  }

  setSession(token: string, role: string): void {
    const normalizedRole = role.startsWith('ROLE_') ? role : `ROLE_${role}`;
    localStorage.setItem('token', token);
    localStorage.setItem('role', normalizedRole);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRole(): string | null {
    return localStorage.getItem('role');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  logout(): void {
    localStorage.clear();

    // Optional: force redirect to home and prevent back navigation
    window.location.replace('/');
  }
}
