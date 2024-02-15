package org.example.springbootAccesoADatos.controlador;

import org.example.springbootAccesoADatos.modelo.dao.ICategoriaDAO;
import org.example.springbootAccesoADatos.modelo.entidades.EntidadCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-biblioteca/categoria")
public class ControladorCategoria {
    @Autowired
    ICategoriaDAO categoriaDAO;

    @GetMapping
    public List<EntidadCategoria> buscarCategorias() {
        return (List<EntidadCategoria>) categoriaDAO.findAll();
    }

    /*@GetMapping("/{id}")
    public EntidadCategoria buscarCategoriaPorId(@PathVariable(value = "id")String idCategoria) {
        return categoriaDAO.findById(idCategoria).get();
    }*/
    @GetMapping("/{id}") //endpoint para buscar un categoria por id
    public ResponseEntity<EntidadCategoria> buscarCategoriaPorId(@PathVariable(value = "id") int id) {
        Optional<EntidadCategoria> categoria = categoriaDAO.findById(id);
        if (categoria.isPresent())
            return ResponseEntity.ok().body(categoria.get());// HTTP 200 OK
        else return ResponseEntity.notFound().build();      // HTTP 404
    }

    @PostMapping
    public EntidadCategoria guardarCategoria(@Validated @RequestBody EntidadCategoria categoria) {
        return categoriaDAO.save(categoria);
    }

    /*@PatchMapping("/{id}")
    public EntidadCategoria actualizarCategoria(@PathVariable(value = "id")String id, @Validated @RequestBody EntidadCategoria categoria) {
        EntidadCategoria categoriaEnBD = categoriaDAO.findById(id).get();
        categoriaEnBD.setCategoria(categoria.getCategoria());
        return categoriaDAO.save(categoriaEnBD);
    }*/
    @CrossOrigin("http://localhost:5173")
    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarCategoria(@RequestBody EntidadCategoria nuevaCategoria, @PathVariable(value = "id") int id) {
        Optional<EntidadCategoria> categoria = categoriaDAO.findById(id);
        if (categoria.isPresent()) {
            categoria.get().setCategoria(nuevaCategoria.getCategoria());
            categoriaDAO.save(categoria.get());
            return ResponseEntity.ok().body("Actualizado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*@DeleteMapping("/{id}")
    public void eliminarCategoria(@PathVariable(value = "id")String id) {
        categoriaDAO.deleteById(id);
    }*/
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarCategoria(@PathVariable(value = "id") int id) {
        Optional<EntidadCategoria> categoria = categoriaDAO.findById(id);
        if (categoria.isPresent()) {
            categoriaDAO.deleteById(id);
            return ResponseEntity.ok().body("Borrado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}