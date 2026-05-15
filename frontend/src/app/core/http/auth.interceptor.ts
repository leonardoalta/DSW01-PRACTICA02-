import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { SessionService } from '../auth/session.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const sessionService = inject(SessionService);
  const authHeader = sessionService.authHeader;

  if (!authHeader) {
    return next(req);
  }

  return next(req.clone({ setHeaders: { Authorization: authHeader } }));
};
