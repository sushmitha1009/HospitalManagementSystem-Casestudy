import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({ providedIn: 'root' })
export class DoctorGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}

  canActivate(): boolean {
    const role = this.auth.getRole()?.toUpperCase();
    if (this.auth.isLoggedIn() && role === 'ROLE_DOCTOR') {
      return true;
    }
    this.auth.logout();
    this.router.navigate(['/doctor-login']);
    return false;
  }
}
