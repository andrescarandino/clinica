package ar.com.dh.clinica.service;

import ar.com.dh.clinica.Dto.OdontologoDto;
import ar.com.dh.clinica.Exceptions.BadRequestException;
import ar.com.dh.clinica.Exceptions.NotFoundException;
import ar.com.dh.clinica.entity.Odontologo;
import ar.com.dh.clinica.repository.IOdontologoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;
import org.xmlunit.util.Mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
@SpringBootTest
class OdontologoServiceTest {

    @InjectMocks
    OdontologoService odontologoService;


    @Mock
    IOdontologoRepository odontologoRepository;
    @Mock
    ValidarOdontologo validarOdontologo;



    @Test
    void guardar_odontologo_correctamente() throws BadRequestException {
        //ARRANGE
        Odontologo odontologo = new Odontologo(1, "andres", "carandino", 5);

        when(odontologoRepository.save(any())).thenReturn(odontologo);

        //ACT
        OdontologoDto odontologoDto = odontologoService.guardar(odontologo);
        //ASSERT
        Assertions.assertEquals("carandino", odontologo.getApellido());
    }


    @Test
    void buscar_odontologo_existente_por_id() throws NotFoundException {
        Odontologo odontologo = new Odontologo(1, "andres", "carandino", 5);
        when(odontologoRepository.findById(any())).thenReturn(Optional.of(odontologo));

        //ACT
        OdontologoDto odontologoDto = odontologoService.buscarPorId(1);
        //ASSERT
        Assertions.assertEquals("andres", odontologoDto.getNombre());
    }

    @Test
    void buscar_odontologo_no_existente_por_id() throws NotFoundException {
        Odontologo odontologo = new Odontologo(1, "andres", "carandino", 5);
        when(odontologoRepository.findById(any())).thenReturn(Optional.empty());

        //ACT
        OdontologoDto odontologoDto = odontologoService.buscarPorId(5);
        //ASSERT
        Assertions.assertEquals("andres", odontologoDto.getNombre());
    }


    @Test
    void listar_odontologos() throws NotFoundException {
        //ARRANGE
        Odontologo odontologo = new Odontologo(1, "pepe", "sanchez", 5);
        Odontologo odontologo1 = new Odontologo(2, "andres", "carandino", 9);
        List<Odontologo> odontologos = Arrays.asList(odontologo, odontologo1);
        when(odontologoRepository.findAll()).thenReturn(odontologos);

        //ACT
        List<OdontologoDto> listaOdontologosDto = odontologoService.listar();
        //ASSERT
        Assertions.assertEquals(2, listaOdontologosDto.size());
    }

    @Test
    void eliminar_odontologo_existente_por_id() throws NotFoundException {
        //ARRANGE
        Odontologo odontologo = new Odontologo(1,"andres", "carandino", 150);
        when(odontologoRepository.findById(any())).thenReturn(Optional.of(odontologo));
        //ACT
        String string = odontologoService.eliminar(1);

        //ASSERT
        assertEquals("Odontologo eliminado con exito", string);
    }

    @Test
    void eliminar_odontologo_que_no_existente_por_id() throws NotFoundException {
        //ARRANGE
        Odontologo odontologo = new Odontologo(1,"andres", "carandino", 150);
        when(odontologoRepository.findById(any())).thenReturn(Optional.empty());
        //ACT
        String string = odontologoService.eliminar(1);

        //ASSERT
        assertEquals("ok", string);
    }

    @Test
    void actualizar_odontologo_correctamente() throws NotFoundException, BadRequestException {
        //ARRANGE
        Odontologo odontologo = new Odontologo(1, "andres", "carandino", 5);

        when(odontologoRepository.save(any())).thenReturn(odontologo);
        when(odontologoRepository.findById(any())).thenReturn(Optional.of(odontologo));

        //ACT
        OdontologoDto odontologoDto = odontologoService.actualizar(odontologo);
        //ASSERT
        Assertions.assertEquals("carandino", odontologoDto.getApellido());
    }

    @Test
    void actualizar_odontologo_que_no_existe() throws NotFoundException, BadRequestException {
        //ARRANGE
        Odontologo odontologo = new Odontologo(1, "andres", "carandino", 5);

        when(odontologoRepository.save(any())).thenReturn(odontologo);


        //ACT
        OdontologoDto odontologoDto = odontologoService.actualizar(odontologo);
        //ASSERT
        Assertions.assertEquals("carandino", odontologoDto.getApellido());
    }



}