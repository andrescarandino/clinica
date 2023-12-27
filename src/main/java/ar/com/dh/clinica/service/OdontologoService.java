package ar.com.dh.clinica.service;

import ar.com.dh.clinica.Dto.OdontologoDto;
import ar.com.dh.clinica.Exceptions.BadRequestException;
import ar.com.dh.clinica.Exceptions.NotFoundException;
import ar.com.dh.clinica.entity.Odontologo;
import ar.com.dh.clinica.repository.IOdontologoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OdontologoService implements Iservice<Odontologo, OdontologoDto> {
    private static final Logger Logger = LogManager.getLogger(OdontologoService.class);
    @Autowired
    IOdontologoRepository odontologoRepository;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    ValidarOdontologo validar;

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
        this.mapper = new ObjectMapper();
        this.validar = new ValidarOdontologo(odontologoRepository);
    }

    //metodo actualizar odontologo
    @Override
    public OdontologoDto guardar(Odontologo odontologo)throws BadRequestException {
        Logger.info("OdontologoService --> guardar()");
        validar.validarNombreYApellidoOdontologo(odontologo.getNombre(), odontologo.getApellido());
        validar.validarMatriculaOdontologo(odontologo.getMatricula());
        validar.validarMatriculaUnicaOdontologo(odontologo.getMatricula());
        OdontologoDto odontologoDto = mapper.convertValue(odontologoRepository.save(odontologo), OdontologoDto.class);
        Logger.info("Se guardo un odontologo correctamente");
        return odontologoDto;
    }
    @Override
    public List<OdontologoDto> listar(){
        Logger.info("OdontologoService --> listar()");
        return odontologoRepository.findAll()
                .stream()
                .map(odontologo -> mapper.convertValue(odontologo, OdontologoDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public OdontologoDto buscarPorId(Integer id) throws NotFoundException {
        Logger.info("OdontologoService --> buscarPorId()");
        Optional<Odontologo> odontologo = odontologoRepository.findById(id);
        if(odontologo.isPresent()){
            Logger.info("Odontologo encontrado");
            return mapper.convertValue(odontologo.get(), OdontologoDto.class);
        } else {
            throw new NotFoundException("code: 07","El odontologo que buscas no existe");
        }
    }

    @Override
    public String eliminar(Integer id) throws NotFoundException{
        Logger.info("OdontologoService --> eliminar()");
        Optional<Odontologo> odontologo = odontologoRepository.findById(id);
        if (odontologo.isPresent()){
            odontologoRepository.deleteById(id);
            Logger.info("Odontologo eliminado");
            return "Odontologo eliminado con exito";
        }else {
            throw new NotFoundException("code: ", "El odontologo con que desea eliminar no existe");
        }
    }

    @Override
    public OdontologoDto actualizar(Odontologo odontologo) throws BadRequestException, NotFoundException {
        Logger.info("OdontologoService --> actualizar()");
        if (odontologo.getId() == null){
            throw new BadRequestException("code: 78", "Es necesario el id del odontologo que quieres modificar");
        }
        if (odontologoRepository.findById(odontologo.getId()).isPresent()){
            validar.validarMatriculaOdontologo(odontologo.getMatricula());
            validar.validarNombreYApellidoOdontologo(odontologo.getNombre(), odontologo.getApellido());
            OdontologoDto odontologoDto = mapper.convertValue(odontologoRepository.save(odontologo), OdontologoDto.class);
            Logger.info("Odontologo actualizado");
           return odontologoDto;
        }
        throw new NotFoundException("Code: 34", "El odontologo que quieres modificar no existe");
    }
}
