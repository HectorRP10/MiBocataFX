package com.example.mibocatafx.dao;

import com.example.mibocatafx.models.Pedido;
import com.example.mibocatafx.util.HibernateUtil;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class PedidoDao {
    public void save(Pedido pedido) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(pedido);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                System.out.println("Pedido no es nulo");
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Pedido> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Pedido", Pedido.class).getResultList();
        }
    }

    public List<Pedido> getPaginated() {
        int pageSize = 10;
        int pageNumber = 2;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Pedido", Pedido.class)
                    .setFirstResult((pageNumber - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        }
    }

    public List<Pedido> getPedidoAlumno(int idAlumno, Date fecha) {
        LocalDate fechaSinHora = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            TypedQuery<Pedido> query = session.createQuery("FROM Pedido WHERE id_alumno = :idAlumno AND CAST(fecha AS localdate) = :fecha", Pedido.class);
            query.setParameter("idAlumno", idAlumno);
            query.setParameter("fecha", fechaSinHora);
            return query.getResultList();
        }
    }

    public void delete(Pedido pedido) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(pedido);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(Pedido pedido) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(pedido);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

}