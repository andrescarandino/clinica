package ar.com.dh.clinica.controller;


import ar.com.dh.clinica.Dto.OdontologoDto;
import ar.com.dh.clinica.Dto.TurnoDto;
import ar.com.dh.clinica.Exceptions.BadRequestException;
import ar.com.dh.clinica.Exceptions.NotFoundException;
import ar.com.dh.clinica.entity.Odontologo;
import ar.com.dh.clinica.entity.Turno;
import ar.com.dh.clinica.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("turnos")
public class TurnoController {

    @Autowired
    TurnoService service;
    @PostMapping()
    public ResponseEntity<TurnoDto> guardar(@RequestBody Turno t) throws BadRequestException, NotFoundException {
        return ResponseEntity.ok(service.guardar(t));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDto> buscarPorId(@PathVariable Integer id){
        TurnoDto turnoDto = service.buscarPorId(id);
        if (turnoDto != null){
            return ResponseEntity.status(HttpStatus.OK).body(turnoDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
    @GetMapping
    public List<TurnoDto> listar(){
        return service.listar();
    }

    @DeleteMapping(path = "/{id}" )
    public ResponseEntity<String> eliminar(@PathVariable Integer id) throws NotFoundException {
            return ResponseEntity.status(HttpStatus.OK).body(service.eliminar(id));
    }

    @PutMapping
    public ResponseEntity<TurnoDto> actualizar(@RequestBody Turno turno) throws BadRequestException, NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(service.actualizar(turno));
    }

}
