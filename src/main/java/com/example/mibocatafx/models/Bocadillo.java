package com.example.mibocatafx.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "bocadillos")
public class Bocadillo {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "nombre",nullable = false)
    private String nombre;
    @Column(name = "precio",nullable = true)
    private double precio;
    @Column(name = "ingredientes",nullable = true)
    private String ingredientes;
    public enum Tipo {
        Caliente,
        Frio
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo",nullable = false)
    private Tipo tipo;

    public enum DiaSemana {
        L,
        M,
        X,
        J,
        V
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana",nullable = true)
    private DiaSemana diaSemana;
    @Column(name = "fecha_baja",nullable = true)
    private Date fecha_baja;

    public Bocadillo(){

    }

    public Bocadillo(int id, String nombre, double precio, String ingredientes, Tipo tipo, DiaSemana diaSemana, Date fecha_baja) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.ingredientes = ingredientes;
        this.tipo = tipo;
        this.diaSemana = diaSemana;
        this.fecha_baja = fecha_baja;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Date getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(Date fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    @Override
    public String toString() {
        return "Bocadillos{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", ingredientes='" + ingredientes + '\'' +
                ", tipo=" + tipo +
                ", diaSemana=" + diaSemana +
                ", fecha_baja=" + fecha_baja +
                '}';
    }
}
