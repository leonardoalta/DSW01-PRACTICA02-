import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { SessionService } from '../../../core/auth/session.service';
import { Empleado, EmpleadosService } from '../empleados.service';

@Component({
  selector: 'app-empleados-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './empleados-list.component.html',
  styleUrl: './empleados-list.component.scss'
})
export class EmpleadosListComponent implements OnInit {
  empleados: Empleado[] = [];
  errorMessage = '';

  constructor(
    private readonly empleadosService: EmpleadosService,
    private readonly sessionService: SessionService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.load();
  }

  get isMaster(): boolean {
    return this.sessionService.isMaster;
  }

  load(): void {
    this.errorMessage = '';
    this.empleadosService.list().subscribe({
      next: (data) => this.empleados = data,
      error: () => this.errorMessage = 'No fue posible cargar empleados'
    });
  }

  delete(clave: string): void {
    if (!this.isMaster) {
      this.errorMessage = 'No autorizado para eliminar empleados';
      return;
    }

    this.empleadosService.remove(clave).subscribe({
      next: () => this.load(),
      error: (err) => {
        this.errorMessage = err?.status === 403
          ? 'No autorizado para eliminar empleados'
          : 'No fue posible eliminar el empleado';
      }
    });
  }

  logout(): void {
    this.sessionService.logout();
    this.router.navigate(['/login']);
  }
}
