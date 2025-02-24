package com.example.mibocatafx.service;

import com.example.mibocatafx.dao.CursoDao;
import com.example.mibocatafx.models.Curso;

import java.util.List;

public class CursoService {

    private final CursoDao cursoDao = new CursoDao();


    public List<Curso> getAll() {
        return cursoDao.getAll();
    }
    public void modificar(Curso curso) {
        cursoDao.update(curso);
    }
    public void eliminar(Curso curso) {
        cursoDao.delete(curso);
    }
    public int obtenerTotalBocadillos() {
        return cursoDao.obtenerTotalCursos();
    }
}
