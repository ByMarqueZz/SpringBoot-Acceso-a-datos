package org.example.springbootAccesoADatos.modelo.entidades;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "Prestamo", schema = "BIBLIOTECA", catalog = "")
public class EntidadPrestamo {
    @Basic
    @Column(name = "idLibro", nullable = true)
    private Integer idLibro;
    @Basic
    @Column(name = "fechaPrestamo", nullable = true)
    private Timestamp fechaPrestamo;
    @ManyToOne
    @JoinColumn(name = "idLibro", referencedColumnName = "id")
    private EntidadLibro libro;
    @ManyToOne
    @JoinColumn(name = "idUsuario", referencedColumnName = "id")
    private EntidadUsuario usuario;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntidadPrestamo that = (EntidadPrestamo) o;

        if (idLibro != null ? !idLibro.equals(that.idLibro) : that.idLibro != null) return false;
        if (fechaPrestamo != null ? !fechaPrestamo.equals(that.fechaPrestamo) : that.fechaPrestamo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idLibro != null ? idLibro.hashCode() : 0;
        result = 31 * result + (fechaPrestamo != null ? fechaPrestamo.hashCode() : 0);
        return result;
    }

    public EntidadLibro getLibro() {
        return libro;
    }

    public void setLibro(EntidadLibro libro) {
        this.libro = libro;
    }

    public EntidadUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(EntidadUsuario usuario) {
        this.usuario = usuario;
    }
}
