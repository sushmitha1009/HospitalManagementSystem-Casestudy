import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({ providedIn: 'root' })
export class AdminGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}

  canActivate(): boolean {
    const role = this.auth.getRole()?.toUpperCase();
    if (this.auth.isLoggedIn() && role === 'ROLE_ADMIN') {
      return true;
    }
    this.auth.logout();
    this.router.navigate(['/admin-login']);
    return false;
  }
}
