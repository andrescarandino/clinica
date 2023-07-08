package ar.com.dh.clinica.repository;

import ar.com.dh.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPacienteRepository extends JpaRepository<Paciente, Integer> {

}
