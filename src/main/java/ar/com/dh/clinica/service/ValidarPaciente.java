package ar.com.dh.clinica.service;

import ar.com.dh.clinica.Exceptions.BadRequestException;
import ar.com.dh.clinica.entity.Odontologo;
import ar.com.dh.clinica.entity.Paciente;
import ar.com.dh.clinica.repository.IPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ValidarPaciente {
    @Autowired
    IPacienteRepository pacienteRepository;
    public void validarDniPaciente(Integer dni) throws BadRequestException {
        if (dni == null){
            throw new BadRequestException("code: 05", "El dni no puede ser nulo");
        }
        if (dni < 0){
            throw new BadRequestException("Code: 11" ,"El dni debe ser mayor a 0");
        }
        if (!(dni.toString().length() == 8)){
           throw new BadRequestException("Code: 12", "El dni debe contener 8 digitos");
        }
        if (dni.toString().matches("[^0-9]")){
            throw new BadRequestException("Code: 14", "El dni debe contener solo caracteres numericos");
        }
    }

    public void validarDniUnico(Integer dni) throws BadRequestException {
        List<Paciente> pacientes = pacienteRepository.findAll();
        if (!(pacientes.isEmpty())) {
            for (Paciente paciente : pacientes) {
                if(dni.equals(paciente.getDni())){
                    throw new BadRequestException("code: 13", "El dni debe ser unico para cada paciente");
                }
            }
        }
    }
    public void validarFechaAlta(LocalDate fecha) throws BadRequestException {
        if (fecha == null){
            throw new BadRequestException("code: 16", "La fecha de alta no puede ser nula");
        }
        if (fecha.isBefore(LocalDate.now())){
            throw new BadRequestException("Code 15", "La fecha de alta no puede ser anterior a la actual");
        }
    }

}
