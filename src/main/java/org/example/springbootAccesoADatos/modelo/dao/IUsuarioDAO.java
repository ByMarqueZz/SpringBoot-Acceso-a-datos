package org.example.springbootAccesoADatos.modelo.dao;

import org.example.springbootAccesoADatos.modelo.entidades.EntidadCategoria;
import org.example.springbootAccesoADatos.modelo.entidades.EntidadUsuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioDAO extends CrudRepository<EntidadUsuario, Integer> {
    //EntidadUsuario findById(int id);
}
