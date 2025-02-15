package com.example.mibocatafx.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_alumno",nullable = false)
    private int id_alumno;
    @Column(name = "id_bocadillo",nullable = false)
    private int id_bocadillo;
    @Column(name = "id_descuento",nullable = true)
    private Integer id_descuento;
    @Column(name = "precio", nullable=true)
    private double precio;
    @Column(name = "fecha", nullable = false)
    private Date fecha;
    @Column(name = "retirado", nullable = true)
    private Date retirado;

    public Pedido(){

    }

    public Pedido(int id, int id_alumno, int id_bocadillo, Integer id_descuento, double precio, Date fecha, Date retirado) {
        this.id = id;
        this.id_alumno = id_alumno;
        this.id_bocadillo = id_bocadillo;
        this.id_descuento = id_descuento;
        this.precio = precio;
        this.fecha = fecha;
        this.retirado = retirado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(int id_alumno) {
        this.id_alumno = id_alumno;
    }

    public int getId_bocadillo() {
        return id_bocadillo;
    }

    public void setId_bocadillo(int id_bocadillo) {
        this.id_bocadillo = id_bocadillo;
    }

    public int getId_descuento() {
        return id_descuento;
    }

    public void setId_descuento(Integer id_descuento) {
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
        return "Pedido{" +
                "id=" + id +
                ", id_alumno=" + id_alumno +
                ", id_bocadillo=" + id_bocadillo +
                ", id_descuento=" + id_descuento +
                ", precio=" + precio +
                ", fecha=" + fecha +
                ", retirado=" + retirado +
                '}';
    }
}
