package com.ejercicio3.service;

import com.ejercicio3.repository.EmpleadoRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoUserDetailsService implements UserDetailsService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoUserDetailsService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return empleadoRepository.findByEmailIgnoreCaseAndActivoTrue(username)
            .map(empleado -> User.withUsername(empleado.getEmail())
                        .password(empleado.getContrasenaHash())
                        .roles("ADMIN")
                        .build())
            .orElseThrow(() -> new UsernameNotFoundException("Empleado no encontrado o inactivo por email: " + username));
    }
}
