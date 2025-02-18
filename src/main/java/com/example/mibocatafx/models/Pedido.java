package com.example.mibocatafx.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_alumno",nullable = false)
    private Alumno alumno;
    @ManyToOne
    @JoinColumn(name = "id_bocadillo",nullable = false)
    private Bocadillo bocadillo;
    @ManyToOne
    @JoinColumn(name = "id_descuento",nullable = true)
    private Descuento id_descuento;
    @Column(name = "precio", nullable=true)
    private double precio;
    @Column(name = "fecha", nullable = false)
    private Date fecha;
    @Column(name = "retirado", nullable = true)
    private Date retirado;

    public Pedido(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Bocadillo getBocadillo() {
        return bocadillo;
    }

    public void setBocadillo(Bocadillo bocadillo) {
        this.bocadillo = bocadillo;
    }

    public Descuento getId_descuento() {
        return id_descuento;
    }

    public void setId_descuento(Descuento id_descuento) {
        this.id_descuento = id_descuento;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getRetirado() {
        return retirado;
    }

    public void setRetirado(Date retirado) {
        this.retirado = retirado;
    }

    @Override
    public String toString() {
        return "Pedidos{" +
                "id=" + id +
                ", alumno=" + alumno.getId() +
                ", bocadillo=" + bocadillo.getNombre() +
                ", descuento=" + id_descuento +
                ", precio=" + precio +
                ", fecha=" + fecha +
                ", retirado=" + retirado +
                '}';
    }
}
