package com.example.mibocatafx.models;

import jakarta.persistence.*;

@Entity
@Table(name = "bocadillos")
public class Bocadillo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String ingredientes;

    @Column(nullable = false)
    private String Dia_semana;

    @Column(nullable = false)
    private Double Precio;

    @Column(nullable = false)
    private String Tipo;

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIngredientes() { // Cambio de nombre del getter
        return ingredientes;
    }

    public String getDia_semana() {
        return Dia_semana;
    }

    public Double getPrecio() {
        return Precio;
    }

    public String getTipo() {
        return Tipo;
    }



    // Constructor vacío
    public Bocadillo() {
    }

    public Bocadillo(Integer id, String nombre, String ingredientes, String dia_semana, Double precio, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        Dia_semana = dia_semana;
        Precio = precio;
        Tipo = tipo;
    }
}
