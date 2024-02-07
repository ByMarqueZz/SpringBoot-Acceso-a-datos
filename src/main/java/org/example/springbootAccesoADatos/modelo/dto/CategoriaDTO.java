package org.example.springbootAccesoADatos.modelo.dto;

public class CategoriaDTO {
    private int id;
    private String categoria;

    public CategoriaDTO(int id, String categoria) {
        this.id = id;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
