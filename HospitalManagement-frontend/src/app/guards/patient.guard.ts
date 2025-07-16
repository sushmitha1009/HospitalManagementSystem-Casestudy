import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({ providedIn: 'root' })
export class PatientGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}

  canActivate(): boolean {
    const role = this.auth.getRole()?.toUpperCase();
    if (this.auth.isLoggedIn() && role === 'ROLE_PATIENT') {
      return true;
    }
    this.auth.logout();
    this.router.navigate(['/patient-login']);
    return false;
  }
}
