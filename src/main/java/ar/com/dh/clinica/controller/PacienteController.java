package ar.com.dh.clinica.controller;


import ar.com.dh.clinica.Dto.OdontologoDto;
import ar.com.dh.clinica.Dto.PacienteDto;
import ar.com.dh.clinica.Exceptions.BadRequestException;
import ar.com.dh.clinica.Exceptions.NotFoundException;
import ar.com.dh.clinica.entity.Domicilio;
import ar.com.dh.clinica.entity.Odontologo;
import ar.com.dh.clinica.entity.Paciente;
import ar.com.dh.clinica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    PacienteService service;
    @PostMapping()
    public ResponseEntity<PacienteDto> guardar(@RequestBody Paciente p) throws BadRequestException {
        return ResponseEntity.ok(service.guardar(p));
    }

    @GetMapping("/{id}")

    public ResponseEntity<PacienteDto> buscarPorId(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id));
    }

    @GetMapping
    public List<PacienteDto> listar() throws NotFoundException {
        return service.listar();
    }

    @DeleteMapping(path = "/{id}" )
    public ResponseEntity<String> eliminar(@PathVariable Integer id) throws NotFoundException {
            return ResponseEntity.status(HttpStatus.OK).body(service.eliminar(id));
    }

    @PutMapping
    public ResponseEntity<PacienteDto> actualizar(@RequestBody Paciente paciente) throws BadRequestException, NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(service.actualizar(paciente));
    }
}
