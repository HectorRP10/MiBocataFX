package com.example.mibocatafx.dao;

import com.example.mibocatafx.models.Bocadillo;
import com.example.mibocatafx.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
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

    public void update(Bocadillo bocadillo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(bocadillo); // Actualiza el bocadillo en la BD
            transaction.commit();
            System.out.println( "Bocadillo actualizado con éxito.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Si hay error, deshacer cambios
            }
            System.err.println("Error al actualizar el bocadillo: " + e.getMessage());
        }
    }

    public void delete(Bocadillo bocadillo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(bocadillo);
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

    public List<Bocadillo> getPaginated(int paginaActual, int bocadilloPorPagina) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Bocadillo", Bocadillo.class)
                    .setFirstResult((paginaActual - 1) * bocadilloPorPagina)
                    .setMaxResults(bocadilloPorPagina)
                    .list();
        }
    }

    public List<Bocadillo> getBocadilloDia(String diaSemana) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Bocadillo.DiaSemana diaSemanaEnum;
            try {
                diaSemanaEnum = Bocadillo.DiaSemana.valueOf(diaSemana.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Día de la semana inválido: " + diaSemana);
            }
            return session.createQuery("FROM Bocadillo WHERE diaSemana = :diaSemana", Bocadillo.class)
                    .setParameter("diaSemana", diaSemanaEnum)
                    .list();
        }
    }

    public int obtenerTotalBocadillos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = (Long) session.createQuery("SELECT COUNT(b) FROM Bocadillo b").uniqueResult();
            return count.intValue();
        } catch (Exception e) {
            System.err.println(" Error al obtener el total de bocadillos: " + e.getMessage());
            return 0;
        }
    }
}