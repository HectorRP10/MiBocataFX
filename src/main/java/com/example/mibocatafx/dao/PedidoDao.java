package com.example.mibocatafx.dao;

import com.example.mibocatafx.models.Pedido;
import com.example.mibocatafx.util.HibernateUtil;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
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

    public List<Pedido> getPaginated(int page, int offset, HashMap<String, String> filtros) {


        // Separando para añadir de forma dinámica los filtros
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("FROM Pedido p WHERE true");

            // Agregar condiciones dinámicas basadas en el HashMap
            if (filtros != null)
                for (String key : filtros.keySet()) {
                    if (key.equals("tipo"))
                        hql.append(" AND p.tipo LIKE :").append(key);
                    else
                        hql.append(" AND p.").append(key).append(" LIKE :").append(key);
                }

            Query<Pedido> query = session.createQuery(hql.toString(), Pedido.class);

            // Asignar valores a los parámetros de la consulta
            if (filtros != null)
                for (HashMap.Entry<String, String> filtro : filtros.entrySet()) {
                    query.setParameter(filtro.getKey(),"%"+filtro.getValue()+"%");
                }

            // Configurar paginación
            query.setFirstResult((page - 1) * offset); // Página actual
            query.setMaxResults(offset); // Límite de resultados

            return query.list();
        }
    }




    public long cout(HashMap<String, String> filtros) {

        // Separando para añadir de forma dinámica los filtros
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("SELECT COUNT(p) FROM Pedido p WHERE true");

            // Agregar condiciones dinámicas basadas en el HashMap
            if (filtros != null)
                for (String key : filtros.keySet()) {
                    if (key.equals("tipo"))
                        hql.append(" AND p.tipo LIKE :").append(key);
                    else
                        hql.append(" AND p.").append(key).append(" LIKE :").append(key);
                }

            Query<Long> query = session.createQuery(hql.toString(), Long.class);

            // Asignar valores a los parámetros de la consulta
            if (filtros != null)
                for (HashMap.Entry<String, String> filtro : filtros.entrySet()) {
                    query.setParameter(filtro.getKey(),"%"+filtro.getValue()+"%");
                }

            return query.getSingleResult();
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