package com.ejercicio3.repository;

import com.ejercicio3.model.Empleado;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, String> {

    Optional<Empleado> findByClaveAndActivoTrue(String clave);

    Optional<Empleado> findByEmailIgnoreCaseAndActivoTrue(String email);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndClaveNot(String email, String clave);

    List<Empleado> findByActivoTrueOrderByClaveAsc();

    List<Empleado> findByDepartamento_IdAndActivoTrueOrderByClaveAsc(Long departamentoId);

    boolean existsByDepartamento_IdAndActivoTrue(Long departamentoId);

    long countByDepartamento_IdAndActivoTrue(Long departamentoId);

    Page<Empleado> findByActivoTrue(Pageable pageable);
}
