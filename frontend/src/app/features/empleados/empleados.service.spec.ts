import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { EmpleadosService } from './empleados.service';

describe('EmpleadosService', () => {
  it('should create', () => {
    TestBed.configureTestingModule({ providers: [provideHttpClient()] });
    const service = TestBed.inject(EmpleadosService);
    expect(service).toBeTruthy();
  });
});
