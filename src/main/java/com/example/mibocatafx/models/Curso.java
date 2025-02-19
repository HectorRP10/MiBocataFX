package com.example.mibocatafx.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="cursos")
public class Curso {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "nombre", nullable = false)
    private String nombre;

    public Curso() {
    }

    public Curso(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
}
