package ar.com.dh.clinica.controller;

import ar.com.dh.clinica.Dto.OdontologoDto;
import ar.com.dh.clinica.Exceptions.BadRequestException;
import ar.com.dh.clinica.Exceptions.NotFoundException;
import ar.com.dh.clinica.entity.Odontologo;
import ar.com.dh.clinica.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("odontologos")
public class OdontologoController {
    @Autowired
    OdontologoService service;
    @PostMapping()
    public ResponseEntity<OdontologoDto> guardar(@RequestBody Odontologo o)throws BadRequestException {
        return ResponseEntity.ok(service.guardar(o));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontologoDto> buscarPorId(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id));
    }

    @GetMapping
    public List<OdontologoDto> listar() throws NotFoundException {
        return service.listar();
    }

    @DeleteMapping(path = "/{id}" )
    public ResponseEntity<String> eliminar(@PathVariable Integer id) throws NotFoundException{
            return ResponseEntity.status(HttpStatus.OK).body(service.eliminar(id));
    }

    @PutMapping
    public ResponseEntity<OdontologoDto> actualizar(@RequestBody Odontologo odontologo) throws BadRequestException, NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(service.actualizar(odontologo));
    }

}
