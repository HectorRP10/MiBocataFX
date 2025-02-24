package com.example.mibocatafx.dao;

import com.example.mibocatafx.models.Curso;
import com.example.mibocatafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class CursoDao {

    public void save(Curso curso) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(curso);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(Curso curso) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(curso); // Actualiza el curso en la BD
            transaction.commit();
            System.out.println( "Curso actualizado con éxito.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Si hay error, deshacer cambios
            }
            System.err.println("Error al actualizar el curso: " + e.getMessage());
        }
    }

    public void delete(Curso curso) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(curso);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Curso> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Curso", Curso.class).list();
        }
    }

    public List<Curso> getPaginated(int paginaActual, int cursosPorPagina) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Curso", Curso.class)
                    .setFirstResult((paginaActual - 1) * cursosPorPagina)
                    .setMaxResults(cursosPorPagina)
                    .list();
        }
    }

    public int obtenerTotalCursos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = (Long) session.createQuery("SELECT COUNT(c) FROM Curso c").uniqueResult();
            return count.intValue();
        } catch (Exception e) {
            System.err.println(" Error al obtener el total de cursos: " + e.getMessage());
            return 0;
        }
    }
}
