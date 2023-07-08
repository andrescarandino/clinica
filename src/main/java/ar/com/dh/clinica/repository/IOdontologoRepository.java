package ar.com.dh.clinica.repository;

import ar.com.dh.clinica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOdontologoRepository extends JpaRepository<Odontologo, Integer> {
}
