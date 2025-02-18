package com.example.mibocatafx.dao;

import com.example.mibocatafx.models.Bocadillo;
import com.example.mibocatafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BocadilloDao {

    public void save(Bocadillo bocadillo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(bocadillo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Bocadillo> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Bocadillo", Bocadillo.class).list();
        }
    }

    public List<Bocadillo> getPaginated() {
        int pageSize = 10;
        int pageNumber = 2;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select new Bocadillos(m.id, m.nombre, m.ingredientes) from Bocadillos m", Bocadillo.class)
                    .setFirstResult((pageNumber - 1) * pageSize) // Salta los primeros 10 registros
                    .setMaxResults(pageSize) // Devuelve 10 registros
                    .list();
        }
    }

    public List<Bocadillo> getByDiaSemana(String diaSemana) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Bocadillo.DiaSemana diaSemanaEnum = Bocadillo.DiaSemana.valueOf(diaSemana);
            return session.createQuery("FROM Bocadillo WHERE diaSemana = :diaSemana", Bocadillo.class)
                    .setParameter("diaSemana", diaSemanaEnum)
                    .list();
        }
    }
}