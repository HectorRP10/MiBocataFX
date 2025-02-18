package com.example.mibocatafx.models;

import jakarta.persistence.*;

@Entity
@Table(name = "descuentos")
public class Descuento {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "nombre",nullable = false)
    private String nombre;

    @Column(name = "descripcion",nullable = true)
    private String descripcion;

    public enum Tipo{
        Unitario,
        Porcentual
    }
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo",nullable = true)
    private Tipo tipo;

    @Column(name = "cantidad")
    private double cantidad;

    public Descuento() {
    }

    public Descuento(int id, String nombre, String descripcion, Tipo tipo, double cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.cantidad = cantidad;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
}
