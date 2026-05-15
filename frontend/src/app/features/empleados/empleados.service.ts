import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiClientService } from '../../core/http/api-client.service';

export interface Empleado {
  clave: string;
  nombre: string;
  telefono: string;
  email: string;
  activo: boolean;
}

export interface EmpleadoRequest {
  nombre: string;
  telefono: string;
  email: string;
  contrasena?: string;
}

@Injectable({ providedIn: 'root' })
export class EmpleadosService {
  constructor(private readonly apiClient: ApiClientService) {}

  list(): Observable<Empleado[]> {
    return this.apiClient.get<Empleado[]>('/empleados');
  }

  create(payload: EmpleadoRequest): Observable<Empleado> {
    return this.apiClient.post<Empleado>('/empleados', payload);
  }

  update(clave: string, payload: EmpleadoRequest): Observable<Empleado> {
    return this.apiClient.put<Empleado>(`/empleados/${clave}`, payload);
  }

  remove(clave: string): Observable<void> {
    return this.apiClient.delete(`/empleados/${clave}`);
  }
}
