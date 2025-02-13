package com.example.mibocatafx.models;


import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "mac", nullable = true)
    private String mac;
    @Column(name = "correo",nullable = false)
    private String correo;
    @Column(name = "contrasenya",nullable = true)
    private String contrasenya;

    public enum Tipo {
        Alumno,
        Admin,
        Cocina
    }
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo",nullable = false)
    private Usuario.Tipo tipo;

    public Usuario(){

    }

    public Usuario(int id, String mac, String correo, String contrasenya, Tipo tipo) {
        this.id = id;
        this.mac = mac;
        this.correo = correo;
        this.contrasenya = contrasenya;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", mac='" + mac + '\'' +
                ", correo='" + correo + '\'' +
                ", contrasenya='" + contrasenya + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}