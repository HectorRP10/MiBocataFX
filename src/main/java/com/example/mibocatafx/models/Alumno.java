package com.example.mibocatafx.models;

import jakarta.persistence.*;


@Entity
@Table(name = "alumnos")
public class Alumno {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "nombre_completo", nullable = false)
    private String nombre;
    @Column(name = "saldo", nullable = true)
    private double saldo;
    @Column(name = "fecha_baja", nullable = true)
    private String fecha_baja;
    @Column(name = "motivo_baja", nullable = true)
    private String motivo_baja;
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = true)
    private Usuario id_usuario;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = true)
    private Curso id_curso;

    public Alumno() {
    }

    public Alumno(int id, String nombre, double saldo, String fecha_baja, String motivo_baja, Usuario id_usuario, Curso id_curso) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
        this.fecha_baja = fecha_baja;
        this.motivo_baja = motivo_baja;
        this.id_usuario = id_usuario;
        this.id_curso = id_curso;
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

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(String fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public String getMotivo_baja() {
        return motivo_baja;
    }

    public void setMotivo_baja(String motivo_baja) {
        this.motivo_baja = motivo_baja;
    }

    public Usuario getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuario id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Curso getId_curso() {
        return id_curso;
    }

    public void setId_curso(Curso id_curso) {
        this.id_curso = id_curso;
    }
}
