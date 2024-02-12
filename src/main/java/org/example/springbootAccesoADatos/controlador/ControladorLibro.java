package org.example.springbootAccesoADatos.controlador;

import org.example.springbootAccesoADatos.modelo.dao.ILibroDAO;
import org.example.springbootAccesoADatos.modelo.entidades.EntidadLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("ControladorLibro")
@RequestMapping()
public class ControladorLibro {
    @Autowired
    ILibroDAO libroDAO;

    @GetMapping
    public List<EntidadLibro> buscarLibros(){
        return (List<EntidadLibro>) libroDAO.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<EntidadLibro> buscarLibroPorId(@PathVariable(value = "id") int id){
        Optional<EntidadLibro> libro = libroDAO.findById(id);
        if (libro.isPresent())
            return ResponseEntity.ok().body(libro.get());
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public EntidadLibro guardarLibro(@Validated @RequestBody EntidadLibro libro){
        return libroDAO.save(libro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarDepartamento (@PathVariable(value = "id") int id) {
        Optional<EntidadLibro> departamento = libroDAO.findById(id);
        if(departamento.isPresent()) {
            libroDAO.deleteById(id);
            return ResponseEntity.ok().body("Borrado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarDepartamento(@RequestBody @Validated EntidadLibro nuevoLibro,
                                                    @PathVariable(value = "id") int id) {
        Optional<EntidadLibro> libro = libroDAO.findById(id);
        if (libro.isPresent()) {
            libro.get().setNombre(nuevoLibro.getNombre());
            libro.get().setAutor(nuevoLibro.getAutor());
            libro.get().setEditorial(nuevoLibro.getEditorial());
            libro.get().setCategoria(nuevoLibro.getCategoria());
            libro.get().setListPrestamos(nuevoLibro.getListPrestamos());
            libroDAO.save(libro.get());
            return ResponseEntity.ok().body("Actualizado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
