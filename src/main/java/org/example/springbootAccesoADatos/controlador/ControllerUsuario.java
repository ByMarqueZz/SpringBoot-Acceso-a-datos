package org.example.springbootAccesoADatos.controlador;

import org.example.springbootAccesoADatos.modelo.dao.IUsuarioDAO;
import org.example.springbootAccesoADatos.modelo.dto.UsuarioDTO;
import org.example.springbootAccesoADatos.modelo.entidades.EntidadUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-biblioteca/usuario")
public class ControllerUsuario {

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @GetMapping
    public List<EntidadUsuario> getUsuario(){
        return (List<EntidadUsuario>) usuarioDAO.findAll();
    }

    @GetMapping("/{id}") //endpoint para buscar un usuario por id
    public ResponseEntity<EntidadUsuario> buscarUsuarioPorId(@PathVariable(value = "id") String id) {
        Optional<EntidadUsuario> usuario = usuarioDAO.findById(id);
        if (usuario.isPresent())
            return ResponseEntity.ok().body(usuario.get());// HTTP 200 OK
        else return ResponseEntity.notFound().build();      // HTTP 404
    }
    @PostMapping("/{id}")
    public EntidadUsuario guardarUsuario(@RequestBody EntidadUsuario usuario){
        return usuarioDAO.save(usuario);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarUsuario(@PathVariable(value = "id") String id) {
        Optional<EntidadUsuario> usuario = usuarioDAO.findById(id);
        if (usuario.isPresent()) {
            usuarioDAO.deleteById(id);
            return ResponseEntity.ok().body("Borrado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Tipo de solicitud HTTP --> PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@RequestBody EntidadUsuario nuevoUsuario, @PathVariable(value = "id") String id) {
        Optional<EntidadUsuario> usuario = usuarioDAO.findById(id);
        if (usuario.isPresent()) {
            usuario.get().setNombre(nuevoUsuario.getNombre());
            usuario.get().setApellidos(nuevoUsuario.getApellidos());
            usuarioDAO.save(usuario.get());
            return ResponseEntity.ok().body("Actualizado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
