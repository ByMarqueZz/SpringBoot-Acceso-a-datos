package org.example.springbootAccesoADatos.controlador;

import org.example.springbootAccesoADatos.modelo.dao.IPrestamoDAO;
import org.example.springbootAccesoADatos.modelo.entidades.EntidadLibro;
import org.example.springbootAccesoADatos.modelo.entidades.EntidadPrestamo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-biblioteca/prestamo")
public class ControladorPrestamo {

    @Autowired
    private IPrestamoDAO prestamoDAO;

    @GetMapping
    public List<EntidadPrestamo> buscarPrestamos() {
        return (List<EntidadPrestamo>) prestamoDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntidadPrestamo> buscarPrestamoPorId(@PathVariable(value = "id") int id) {
        Optional<EntidadPrestamo> prestamo = prestamoDAO.findById(id);
        if (prestamo.isPresent())
            return ResponseEntity.ok().body(prestamo.get());
        else return ResponseEntity.notFound().build();// HTTP 404
    }

    @PostMapping
    public EntidadPrestamo guardarPrestamo(@Validated @RequestBody EntidadPrestamo prestamo) {
        return prestamoDAO.save(prestamo);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<?> actualizarPrestamo(@RequestBody @Validated EntidadPrestamo nuevoPrestamo,
                                                @PathVariable(value = "id") int id) {
        Optional<EntidadPrestamo> prestamo = prestamoDAO.findById(id);
        if (prestamo.isPresent()) {
            prestamo.get().setFechaPrestamo(nuevoPrestamo.getFechaPrestamo());
            prestamo.get().setLibro(nuevoPrestamo.getLibro());
            prestamo.get().setUsuario(nuevoPrestamo.getUsuario());
            prestamoDAO.save(prestamo.get());
            return ResponseEntity.ok().body("Actualizado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarPrestamo(@PathVariable(value = "id") int id) {
        Optional<EntidadPrestamo> prestamo = prestamoDAO.findById(id);
        if (prestamo.isPresent()) {
            prestamoDAO.deleteById(id);
            return ResponseEntity.ok().body("Borrado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
