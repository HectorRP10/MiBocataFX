package com.example.mibocatafx.dao;

import com.example.mibocatafx.models.Usuario;
import com.example.mibocatafx.util.HibernateUtil;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UsuarioDao {
    public void save(Usuario usuario) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(usuario);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Usuario> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Usuario", Usuario.class).list();
        }
    }

    public List<Usuario> getPaginated() {
        int pageSize = 10;
        int pageNumber = 2;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select new Usuarios(m.id, m.correo) from Usuarios m", Usuario.class)
                    .setFirstResult((pageNumber - 1) * pageSize) // Salta los primeros 10 registros
                    .setMaxResults(pageSize) // Devuelve 10 registros
                    .list();
        }
    }



    public Usuario validar_login(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            TypedQuery<Usuario> query = session.createQuery(
                    "FROM Usuario WHERE correo = :email AND contrasenya = :password", Usuario.class);
            query.setParameter("email", email);
            query.setParameter("password", password);

            try {
                return query.getSingleResult(); // Devuelve el usuario
            } catch (NoResultException e) {
                return null; // Null si no coge el usuario
            }
        }
    }

}