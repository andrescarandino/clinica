package ar.com.dh.clinica.repository;

import ar.com.dh.clinica.entity.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDomicilioRepository extends JpaRepository<Domicilio, Integer> {
}
