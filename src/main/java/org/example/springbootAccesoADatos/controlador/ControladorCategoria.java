package org.example.springbootAccesoADatos.controlador;

import org.example.springbootAccesoADatos.modelo.dao.ICategoriaDAO;
import org.example.springbootAccesoADatos.modelo.entidades.EntidadCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-biblioteca/categoria")
public class ControladorCategoria {
    @Autowired
    private ICategoriaDAO categoriaDAO;

    @GetMapping
    public List<EntidadCategoria> obtenerCategorias() {
        return (List<EntidadCategoria>) categoriaDAO.findAll();
    }

    @GetMapping("/{id}")
    public EntidadCategoria obtenerCategoriaPorId(@PathVariable(value = "id")int id) {
        return categoriaDAO.findById(id).get();
    }

    @PostMapping
    public EntidadCategoria guardarCategoria(@Validated @RequestBody EntidadCategoria categoria) {
        return categoriaDAO.save(categoria);
    }

    @PatchMapping("/{id}")
    public EntidadCategoria actualizarCategoria(@PathVariable(value = "id")int id, @Validated @RequestBody EntidadCategoria categoria) {
        EntidadCategoria categoriaEnBD = categoriaDAO.findById(id).get();
        categoriaEnBD.setCategoria(categoria.getCategoria());
        return categoriaDAO.save(categoriaEnBD);
    }

    @DeleteMapping("/{id}")
    public void eliminarCategoria(@PathVariable(value = "id")int id) {
        categoriaDAO.deleteById(id);
    }

}
