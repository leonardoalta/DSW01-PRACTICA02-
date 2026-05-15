import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class SessionService {
  private readonly authHeaderKey = 'auth_header';
  private readonly principalKey = 'principal_email';
  private readonly masterKey = 'is_master';

  private readonly authenticated$ = new BehaviorSubject<boolean>(this.hasValidSession());

  readonly isAuthenticated$ = this.authenticated$.asObservable();

  get authHeader(): string | null {
    return localStorage.getItem(this.authHeaderKey);
  }

  get principalEmail(): string | null {
    return localStorage.getItem(this.principalKey);
  }

  get isMaster(): boolean {
    return localStorage.getItem(this.masterKey) === 'true';
  }

  login(email: string, password: string): void {
    const authHeader = `Basic ${btoa(`${email}:${password}`)}`;
    localStorage.setItem(this.authHeaderKey, authHeader);
    localStorage.setItem(this.principalKey, email);
    localStorage.setItem(this.masterKey, String(email.toLowerCase() === environment.masterPrincipal.toLowerCase()));
    this.authenticated$.next(true);
  }

  logout(): void {
    localStorage.removeItem(this.authHeaderKey);
    localStorage.removeItem(this.principalKey);
    localStorage.removeItem(this.masterKey);
    this.authenticated$.next(false);
  }

  private hasValidSession(): boolean {
    return !!localStorage.getItem(this.authHeaderKey);
  }
}
