package com.example.mibocatafx.models;


import jakarta.persistence.*;

@Entity
@Table(name = "bocadillos")
public class Bocadillo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String ingredientes;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getingredientes() {
        return ingredientes;
    }

    public void setingredientes(String apellido1) {
        this.ingredientes = apellido1;
    }

    @Override
    public String toString() {
        return "Bocadillo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ingredientes='" + ingredientes + '\'' +
                '}';
    }

    // Constructor vacío
    public Bocadillo() {
    }

    // Constructor con parámetros
    public Bocadillo(Long id, String nombre, String apellido1) {
        this.id = id;
        this.nombre = nombre;
        this.ingredientes = apellido1;
    }
}
