package org.example.springbootAccesoADatos.modelo.dao;

import org.example.springbootAccesoADatos.modelo.entidades.EntidadLibro;
import org.springframework.data.repository.CrudRepository;

public interface ILibroDAO extends CrudRepository<EntidadLibro, Integer> {
    EntidadLibro findByNombreIgnoreCase(String nombre);
}
