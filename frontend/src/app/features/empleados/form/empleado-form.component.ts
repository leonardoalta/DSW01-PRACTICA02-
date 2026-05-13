import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { EmpleadoRequest, EmpleadosService } from '../empleados.service';
import { SessionService } from '../../../core/auth/session.service';

@Component({
  selector: 'app-empleado-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './empleado-form.component.html',
  styleUrl: './empleado-form.component.scss'
})
export class EmpleadoFormComponent implements OnInit {
  clave: string | null = null;
  payload: EmpleadoRequest = { nombre: '', telefono: '', email: '', contrasena: '' };
  errorMessage = '';

  constructor(
    private readonly empleadosService: EmpleadosService,
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly sessionService: SessionService
  ) {}

  ngOnInit(): void {
    this.clave = this.route.snapshot.paramMap.get('clave');
  }

  get isEdit(): boolean {
    return !!this.clave;
  }

  save(): void {
    if (!this.sessionService.isMaster) {
      this.errorMessage = 'No autorizado para operaciones de escritura';
      return;
    }

    this.errorMessage = '';
    if (!this.isValidEmail(this.payload.email)) {
      this.errorMessage = 'El email no tiene un formato válido';
      return;
    }

    if (this.isEdit && this.clave) {
      this.empleadosService.update(this.clave, this.payload).subscribe({
        next: () => this.router.navigate(['/empleados']),
        error: (err) => this.handleError(err?.status)
      });
      return;
    }

    this.empleadosService.create(this.payload).subscribe({
      next: () => this.router.navigate(['/empleados']),
      error: (err) => this.handleError(err?.status)
    });
  }

  private isValidEmail(email: string): boolean {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  }

  private handleError(status?: number): void {
    if (status === 409) {
      this.errorMessage = 'Conflicto: email o datos duplicados';
      return;
    }

    if (status === 400) {
      this.errorMessage = 'Error de validación en los datos ingresados';
      return;
    }

    if (status === 403) {
      this.errorMessage = 'No autorizado para operaciones de escritura';
      return;
    }

    this.errorMessage = 'No fue posible guardar el empleado';
  }
}
