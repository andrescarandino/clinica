package ar.com.dh.clinica.service;

import ar.com.dh.clinica.Exceptions.BadRequestException;
import ar.com.dh.clinica.entity.Turno;
import ar.com.dh.clinica.repository.IOdontologoRepository;
import ar.com.dh.clinica.repository.IPacienteRepository;
import ar.com.dh.clinica.repository.ITurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class ValidarTurno {
    //2 turnos no pueden ser con el mismo odontologo a la misma hs
    //no puede haber 2 turnos para el mismo paciente en el dia
    @Autowired
    IOdontologoRepository odontologoRepository;
    @Autowired
    IPacienteRepository pacienteRepository;
    @Autowired
    ITurnoRepository turnoRepository;
    public void validarFechaTurno(LocalDate fecha) throws BadRequestException {
        DayOfWeek diaSemana = fecha.getDayOfWeek();
        if (diaSemana == DayOfWeek.SATURDAY ){
            throw new BadRequestException("Code: 21","No se puede guardar un turno el sabado");
        }
        if (diaSemana == DayOfWeek.SUNDAY){
            throw new BadRequestException("Code: 22","No se puede guardar un turno el domingo");
        }
        if (fecha.isBefore(LocalDate.now()) || fecha.isEqual(LocalDate.now())){
            throw new BadRequestException("Code: 23", "La fecha del turno no puede ser menor o igual a la actual");
        }
    }

    public void validarPacienteYOdontologoTurno(Integer idPaciente, Integer idOdontologo) throws BadRequestException {

        if (!(pacienteRepository.findById(idPaciente).isPresent())){
           throw new BadRequestException("Code: 24", "El paciente con el que desea agendar un turno no existe");
        }
        if (!(odontologoRepository.findById(idOdontologo).isPresent())){
            throw new BadRequestException("code: 25", "El odontologo con el que desea agendar un turno no existe");
        }
    }
    public void validarHoraTurno(LocalTime hora) throws BadRequestException {
        if (hora.isBefore(LocalTime.of(8,0)) || hora.isAfter(LocalTime.of(17,0))){
            throw new BadRequestException("Code: 26", "La hora del turno debe ser entre las 8:00 y 17:00");
        }
    }

    public void validarTurnoRepetido(Turno turno) throws BadRequestException {
        List<Turno> turnos = turnoRepository.findAll();
        Integer idPaciente = turno.getPaciente().getId();
        Integer idOdontologo = turno.getOdontologo().getId();
        LocalDateTime fechaYHoraTurno = turno.getFecha().atTime(turno.getHora());
        if (!(turnos.isEmpty())){
            for (Turno turno1: turnos) {
                LocalDateTime fechaYHoraExistentes = turno1.getFecha().atTime(turno1.getHora());

                if( ( fechaYHoraTurno.isBefore(fechaYHoraExistentes.plusMinutes(30)) || fechaYHoraTurno.isEqual(fechaYHoraExistentes))
                        && turno1.getOdontologo().getId().equals(idOdontologo)){
                    throw new BadRequestException("Code: 27", "El odontologo no puede tener 2 turnos a la misma hora" +
                            " debe haber un minimo de 30 minutos de diferencia entre turno y turno");
                }
                if (turno1.getFecha().isEqual(turno.getFecha()) && turno1.getPaciente().getId().equals(idPaciente)){
                    throw new BadRequestException("code: 28", "El paciente no puede tener 2 turnos el mismo dia");
                }
            }
        }

    }

}
