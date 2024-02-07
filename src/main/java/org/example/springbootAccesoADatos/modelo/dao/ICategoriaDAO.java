package org.example.springbootAccesoADatos.modelo.dao;

import org.example.springbootAccesoADatos.modelo.entidades.EntidadCategoria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaDAO extends CrudRepository<EntidadCategoria, String> {
    EntidadCategoria findByCategoriaIgnoreCase(String categoria);
}
