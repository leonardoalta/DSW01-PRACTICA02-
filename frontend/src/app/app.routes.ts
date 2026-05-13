import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { EmpleadosListComponent } from './features/empleados/list/empleados-list.component';
import { EmpleadoFormComponent } from './features/empleados/form/empleado-form.component';
import { authGuard } from './core/guards/auth.guard';
import { masterGuard } from './core/guards/master.guard';

export const appRoutes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'empleados', component: EmpleadosListComponent, canActivate: [authGuard] },
  { path: 'empleados/nuevo', component: EmpleadoFormComponent, canActivate: [authGuard, masterGuard] },
  { path: 'empleados/:clave/editar', component: EmpleadoFormComponent, canActivate: [authGuard, masterGuard] },
  { path: '', pathMatch: 'full', redirectTo: 'login' },
  { path: '**', redirectTo: 'login' }
];
