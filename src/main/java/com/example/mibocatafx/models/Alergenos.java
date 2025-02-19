package com.example.mibocatafx.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "alergenos")
public class Alergenos {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "nombre",nullable = false)
    private String nombre;
    @Column(name = "icono", nullable = true)
    private String icono;
    @Column(name="descripcion", nullable = true)
    private String descripcion;

    public Alergenos() {
    }

    public Alergenos(int id, String nombre, String icono, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.icono = icono;
        this.descripcion = descripcion;
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

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
