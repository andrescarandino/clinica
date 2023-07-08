package ar.com.dh.clinica.service;

import ar.com.dh.clinica.Exceptions.BadRequestException;
import ar.com.dh.clinica.entity.Odontologo;
import ar.com.dh.clinica.repository.IOdontologoRepository;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ValidarOdontologo {

    @Autowired
    IOdontologoRepository odontologoRepository;

    public ValidarOdontologo(IOdontologoRepository odontologoRepository){
        this.odontologoRepository = odontologoRepository;
    }


    public void validarMatriculaOdontologo(Integer matricula) throws BadRequestException {
        if (matricula < 0){
            throw new BadRequestException("code: 01", "La matricula no puede ser negativa");
        }
    }

    public void validarMatriculaUnicaOdontologo(Integer matricula) throws BadRequestException {
        List<Odontologo> odontologos = odontologoRepository.findAll();
        if (!odontologos.isEmpty()) {
            for (Odontologo odontolgo : odontologos) {
                if(matricula.equals(odontolgo.getMatricula())){
                    throw new BadRequestException("code: 02", "La matricula debe ser unica para cada odontologo");
                }
            }
        }
    }
    public void validarNombreYApellidoOdontologo(String nombre, String apellido) throws BadRequestException {

        if (nombre == null){
            throw new BadRequestException("code: 03", "El nombre no puede ser nulo");
        }
        if (nombre.isEmpty()){
            throw new BadRequestException("code: 03", "El nombre no puede estar vacio");
        }
        if (apellido == null){
            throw new BadRequestException("code: 04", "El apellido no puede ser nulo");
        }
        if (apellido.isEmpty()){
            throw new BadRequestException("code: 05", "El apellido no puede estar vacio");
        }
        if (nombre.length() < 3){
            throw new BadRequestException("code: 06", "El nombre debe contener un minimo de 3 caracteres");
        }
        if (apellido.length() < 3){
            throw new BadRequestException("code: 05", "El apellido debe contener un mino de 3 caracteres");
        }
    }
}
