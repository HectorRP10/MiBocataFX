package com.example.mibocatafx.service;

import com.example.mibocatafx.dao.BocadilloDao;
import com.example.mibocatafx.models.Bocadillo;
import java.util.List;

public class BocadilloService {

    private final BocadilloDao bocadilloDao = new BocadilloDao();

    public void save(Bocadillo bocadillo) {
        // Validación antes de guardar
        if (bocadillo.getId() == null || bocadillo.getId() == 0) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }

        bocadilloDao.save(bocadillo);
    }

    public List<Bocadillo> getAll() {
        return bocadilloDao.getAll();
    }

    public List<Bocadillo> getPaginated() {
        return bocadilloDao.getPaginated();
    }
}
