package ar.com.dh.clinica.service;


import ar.com.dh.clinica.Dto.PacienteDto;
import ar.com.dh.clinica.Exceptions.BadRequestException;
import ar.com.dh.clinica.Exceptions.NotFoundException;

import ar.com.dh.clinica.entity.Paciente;
import ar.com.dh.clinica.repository.IDomicilioRepository;
import ar.com.dh.clinica.repository.IPacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService implements Iservice<Paciente, PacienteDto> {

    private static final org.apache.log4j.Logger Logger = LogManager.getLogger(PacienteService.class);
    @Autowired
    IPacienteRepository pacienteRepository;
    @Autowired
    IDomicilioRepository domicilioRepository;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    ValidarPaciente validar;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public PacienteDto guardar(Paciente paciente) throws BadRequestException {
        Logger.info("PacienteService --> guardar()");
        validar.validarDniPaciente(paciente.getDni());
        validar.validarDniUnico(paciente.getDni());
        validar.validarFechaAlta(paciente.getFechaAlta());
        PacienteDto pacienteDto = mapper.convertValue(pacienteRepository.save(paciente), PacienteDto.class);
        Logger.info("odontologo guardado");
        return pacienteDto;
    }

    @Override
    public List<PacienteDto> listar() throws NotFoundException {
        Logger.info("PacienteService --> listar()");
        return pacienteRepository.findAll()
                .stream()
                .map(paciente -> mapper.convertValue(paciente, PacienteDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PacienteDto buscarPorId(Integer id) throws NotFoundException {
        Logger.info("PacienteService --> buscarPorId()");
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        if(paciente.isPresent()){
            Logger.info("paciente encontrado");
            return mapper.convertValue(paciente.get(), PacienteDto.class);
        } else {
            throw new NotFoundException("code: 13","El paciente que buscas no existe");
        }
    }

    @Override
    public String eliminar(Integer id) throws NotFoundException {
        Logger.info("PacienteService --> eliminar()");
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        if (paciente.isPresent()){
            pacienteRepository.deleteById(id);
            Logger.info("paciente eliminado");
            return "Paciente eliminado con exito";
        }else {
            throw new NotFoundException("code: 14", "El paciente que desea eliminar no existe");
        }
    }

    @Override
    public PacienteDto actualizar(Paciente paciente) throws BadRequestException, NotFoundException {
        Logger.info("PacienteService --> actualizar()");
        if (paciente.getId() == null){
            throw new BadRequestException("code: 77", "Es necesario el id del paciente que quieres actualizar");
        }
        Optional<Paciente> paciente1 = pacienteRepository.findById(paciente.getId());
        if (paciente1.isPresent()){
            validar.validarDniPaciente(paciente.getDni());
            PacienteDto pacienteDto = mapper.convertValue(pacienteRepository.save(paciente), PacienteDto.class);
            Logger.info("paciente actualizado");
            return pacienteDto;
        }
        throw new NotFoundException("code: 33", "El paciente que queres actualizar no existe");
    }

}
