package com.ejercicio3.repository;

import com.ejercicio3.model.Empleado;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, String> {

    Optional<Empleado> findByClaveAndActivoTrue(String clave);

    List<Empleado> findByActivoTrueOrderByClaveAsc();

    Page<Empleado> findByActivoTrue(Pageable pageable);
}
