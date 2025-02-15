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
            session.persist(pedido);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
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
            return session.createQuery("select new Pedidos(m.id, m.id_usuario, m.precio) from Pedidos m", Pedido.class)
                    .setFirstResult((pageNumber - 1) * pageSize) // Salta los primeros 10 registros
                    .setMaxResults(pageSize) // Devuelve 10 registros
                    .list();
        }
    }
}
