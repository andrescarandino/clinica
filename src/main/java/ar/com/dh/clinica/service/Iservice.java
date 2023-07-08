package ar.com.dh.clinica.service;

import ar.com.dh.clinica.Exceptions.BadRequestException;
import ar.com.dh.clinica.Exceptions.NotFoundException;

import java.util.List;

public interface Iservice<T, E> {

    E guardar(T t) throws BadRequestException, NotFoundException;

    List<E> listar() throws NotFoundException;

    public E buscarPorId(Integer id) throws NotFoundException;

    public String eliminar(Integer id) throws NotFoundException;

    public E actualizar(T t)throws BadRequestException, NotFoundException;


}
