package ar.com.dh.clinica.repository;

import ar.com.dh.clinica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITurnoRepository extends JpaRepository<Turno, Integer> {
}
