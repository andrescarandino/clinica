package ar.com.dh.clinica.service;

import ar.com.dh.clinica.Dto.TurnoDto;
import ar.com.dh.clinica.Exceptions.BadRequestException;
import ar.com.dh.clinica.Exceptions.NotFoundException;

import ar.com.dh.clinica.entity.Turno;

import ar.com.dh.clinica.repository.ITurnoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class TurnoService implements Iservice<Turno, TurnoDto> {

    private static final org.apache.log4j.Logger Logger = LogManager.getLogger(OdontologoService.class);
    @Autowired
    ITurnoRepository turnoRepository;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    OdontologoService odontologoService;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    ValidarTurno validar;
    public TurnoService(ITurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    @Override
    public TurnoDto guardar(Turno turno) throws BadRequestException, NotFoundException {
        Logger.info("TurnoService --> guardar()");
        validar.validarPacienteYOdontologoTurno(turno.getPaciente().getId(), turno.getOdontologo().getId());
        validar.validarFechaTurno(turno.getFecha());
        validar.validarHoraTurno(turno.getHora());
        validar.validarTurnoRepetido(turno);
        TurnoDto turnoDto = mapper.convertValue(turnoRepository.save(turno), TurnoDto.class);
        Logger.info("Turno guardado");
        turnoDto.setOdontologo(odontologoService.buscarPorId(turnoDto.getOdontologo().getId()));
        turnoDto.setPaciente(pacienteService.buscarPorId(turnoDto.getPaciente().getId()));
        return turnoDto;
    }

    @Override
    public List<TurnoDto> listar() {
        Logger.info("TurnoService --> Listar()");
        return turnoRepository.findAll()
                .stream()
                .map(turno -> mapper.convertValue(turno, TurnoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TurnoDto buscarPorId(Integer id) {
        Logger.info("TurnoService --> Listar()");
        TurnoDto turnoDto = mapper.convertValue(turnoRepository.findById(id), TurnoDto.class);
        Logger.info("Turno encontrado");
        return turnoDto;
    }

    @Override
    public String eliminar(Integer id) throws NotFoundException {
        Logger.info("TurnoService --> eliminar()");
        Optional<Turno> turno = turnoRepository.findById(id);
        if (turno.isPresent()){
            turnoRepository.deleteById(id);
            Logger.info("Turno Eliminado");
            return "Odontologo eliminado con exito";
        }else {
            throw new NotFoundException("code: 41", "El odontologo con que desea eliminar no existe");
        }
    }

    @Override
    public TurnoDto actualizar(Turno turno) throws NotFoundException, BadRequestException {
        Logger.info("TurnoService --> actualizar()");
        if (turno.getId() == null){
            throw new BadRequestException("code 45:", "Es necesario el id del turno que quieres actualizar" );
        }
        if (turnoRepository.findById(turno.getPaciente().getId()).isPresent()) {
            validar.validarPacienteYOdontologoTurno(turno.getPaciente().getId(), turno.getOdontologo().getId());
            validar.validarFechaTurno(turno.getFecha());
            validar.validarHoraTurno(turno.getHora());
            TurnoDto turnoDto = mapper.convertValue(turnoRepository.save(turno), TurnoDto.class);
            Logger.info("Turno actualizado");
            return turnoDto;
        }
        throw new NotFoundException("code: 42", "El recurso que deseas actualizar no existe");
    }

}
