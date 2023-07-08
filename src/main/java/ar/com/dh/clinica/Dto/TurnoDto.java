package ar.com.dh.clinica.Dto;

import ar.com.dh.clinica.entity.Odontologo;
import ar.com.dh.clinica.entity.Paciente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@JsonIgnoreProperties
public class TurnoDto {

    private Integer id;
    private LocalDate fecha;
    private LocalTime hora;
    private PacienteDto paciente;
    private OdontologoDto odontologo;

    public TurnoDto(Integer id, LocalDate fecha, LocalTime hora, PacienteDto paciente, OdontologoDto odontologo) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.paciente = paciente;
        this.odontologo = odontologo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public PacienteDto getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDto paciente) {
        this.paciente = paciente;
    }

    public OdontologoDto getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(OdontologoDto odontologo) {
        this.odontologo = odontologo;
    }
}
