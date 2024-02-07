package org.example.springbootAccesoADatos.controlador;

import org.example.springbootAccesoADatos.modelo.dao.IPrestamoDAO;
import org.example.springbootAccesoADatos.modelo.entidades.EntidadPrestamo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-biblioteca/prestamo")
public class ControllerPrestamo {

    @Autowired
    private IPrestamoDAO prestamoDAO;

    @GetMapping
    public List<EntidadPrestamo> getPrestamos() {
        return (List<EntidadPrestamo>) prestamoDAO.findAll();
    }

    @GetMapping("/{id}")
    public Object buscarPrestamoPorId(@PathVariable(value = "id") int idPrestamo) {
        Optional<EntidadPrestamo> departamento = prestamoDAO.findById(idPrestamo);
        if (departamento.isPresent())
            return prestamoDAO.findById(idPrestamo).get();
        else return ResponseEntity.notFound().build(); // HTTP 404
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<EntidadPrestamo> buscarPrestamoDTOPorId(@PathVariable(value = "id") int idPrestamo) {
        Optional<EntidadPrestamo> prestamo = prestamoDAO.findById(idPrestamo);
        if (prestamo.isPresent())
            return ResponseEntity.ok().body(prestamo.get());// HTTP 200 OK
        else return ResponseEntity.notFound().build();      // HTTP 404
    }

    @PostMapping
    public EntidadPrestamo guardarPrestamo(@Validated @RequestBody EntidadPrestamo prestamo) {
        return prestamoDAO.save(prestamo);
    }

    //Usamos Patch para no pasar el objeto completo , solo los campos que queremos actualizar
    @PatchMapping ("/{id}")
    public ResponseEntity<EntidadPrestamo> actualizarPrestamo(@Validated @PathVariable(value = "id") int idPrestamo, @RequestBody EntidadPrestamo prestamo) {
        Optional<EntidadPrestamo> prestamoEncontrado = prestamoDAO.findById(idPrestamo);
        if (prestamoEncontrado.isPresent()) {
            EntidadPrestamo prest = prestamoEncontrado.get();
            if (prestamo.getLibro() != null) {
                prest.setLibro(prestamo.getLibro());
            }
            if (prestamo.getFechaPrestamo() != null) {
                prest.setFechaPrestamo(prestamo.getFechaPrestamo());
            }
            if(prestamo.getUsuario() != null){
                prest.setUsuario(prestamo.getUsuario());
            }
            prestamoDAO.save(prest);
            return ResponseEntity.ok().body(prest);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarPrestamo(@PathVariable(value = "id") int idPrestamo) {
        Optional<EntidadPrestamo> prestamo = prestamoDAO.findById(idPrestamo);
        if (prestamo.isPresent()) {
            prestamoDAO.deleteById(idPrestamo);
            return ResponseEntity.ok().body("Borrado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
