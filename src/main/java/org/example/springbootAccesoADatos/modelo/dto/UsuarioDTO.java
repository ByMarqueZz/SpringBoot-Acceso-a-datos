package org.example.springbootAccesoADatos.modelo.dto;

public class UsuarioDTO {
    private int id;
    private String nombre;
    private String apellidos;

    public UsuarioDTO(int id, String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public UsuarioDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
