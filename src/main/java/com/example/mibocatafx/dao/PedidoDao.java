package com.example.mibocatafx.dao;

import com.example.mibocatafx.models.Pedido;
import com.example.mibocatafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
            return session.createQuery("FROM Pedido", Pedido.class).list();
        }
    }

    public List<Pedido> getPaginated() {
        int pageSize = 10;
        int pageNumber = 2;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Pedido", Pedido.class)
                    .setFirstResult((pageNumber - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .list();
        }
    }
}