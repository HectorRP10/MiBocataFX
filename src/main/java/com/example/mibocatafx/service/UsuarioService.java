package com.example.mibocatafx.service;

import com.example.mibocatafx.dao.BocadilloDao;
import com.example.mibocatafx.dao.UsuarioDao;
import com.example.mibocatafx.models.Bocadillo;
import com.example.mibocatafx.models.Usuario;

import java.util.List;

public class UsuarioService {
    private final UsuarioDao usuarioDao = new UsuarioDao();

    public void save(Usuario usuario) {
        // Validación antes de guardar
        if (usuario.getId() == 0) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }

        usuarioDao.save(usuario);
    }

    public List<Usuario> getAll() {
        return usuarioDao.getAll();
    }

    public List<Usuario> getPaginated() {
        return usuarioDao.getPaginated();
    }
}
