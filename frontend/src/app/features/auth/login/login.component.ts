import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiClientService } from '../../../core/http/api-client.service';
import { SessionService } from '../../../core/auth/session.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  email = '';
  contrasena = '';
  errorMessage = '';
  loading = false;

  constructor(
    private readonly apiClient: ApiClientService,
    private readonly sessionService: SessionService,
    private readonly router: Router
  ) {}

  submit(): void {
    this.errorMessage = '';
    this.loading = true;
    this.sessionService.login(this.email, this.contrasena);

    this.apiClient.get<unknown>('/empleados')
      .subscribe({
        next: () => {
          this.loading = false;
          this.router.navigate(['/empleados']);
        },
        error: () => {
          this.loading = false;
          this.sessionService.logout();
          this.errorMessage = 'Credenciales inválidas';
        }
      });
  }
}
