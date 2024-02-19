package org.example.springbootAccesoADatos.modelo.dto;

public class LibroDTO {
    private int id;
    private String autor;
    private String editorial;
    private int idCategoria;

    public LibroDTO(int id, String autor, String editorial, int idCategoria) {
        this.id = id;
        this.autor = autor;
        this.editorial = editorial;
        this.idCategoria = idCategoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
