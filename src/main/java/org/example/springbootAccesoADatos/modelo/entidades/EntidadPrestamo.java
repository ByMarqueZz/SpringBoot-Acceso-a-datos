package org.example.springbootAccesoADatos.modelo.entidades;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "prestamo", schema = "BIBLIOTECA", catalog = "")
public class EntidadPrestamo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "fechaPrestamo", nullable = true)
    private Timestamp fechaPrestamo;
    @ManyToOne
    @JoinColumn(name = "idLibro", referencedColumnName = "id")
    private EntidadLibro libro;
    @ManyToOne
    @JoinColumn(name = "idUsuario", referencedColumnName = "id")
    private EntidadUsuario usuario;

    public Timestamp getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Timestamp fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
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
