package org.example.springbootAccesoADatos.modelo.dao;

import org.example.springbootAccesoADatos.modelo.entidades.EntidadPrestamo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
    public interface IPrestamoDAO extends CrudRepository<EntidadPrestamo, Integer> {
        //Optional<EntidadPrestamo> findById(int id);
    }