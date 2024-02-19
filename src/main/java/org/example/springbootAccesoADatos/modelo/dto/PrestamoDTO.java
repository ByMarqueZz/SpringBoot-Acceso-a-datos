package org.example.springbootAccesoADatos.modelo.dto;

import java.sql.Timestamp;

public class PrestamoDTO {

    private Integer idLibro;
    private Timestamp fechaPrestamo;
    private int idUsuario;
    private int idPrestamo;

    public PrestamoDTO(Integer idLibro , Timestamp fechaPrestamo, int idUsuario, int idPrestamo) {
        this.idLibro = idLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.idUsuario = idUsuario;
        this.idPrestamo = idPrestamo;

    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public Timestamp getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Timestamp fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }
}
