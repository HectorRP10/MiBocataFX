package com.example.mibocatafx.dao;

import com.example.mibocatafx.models.Alumno;
import com.example.mibocatafx.models.Bocadillo;
import com.example.mibocatafx.models.Pedido;
import com.example.mibocatafx.models.Usuario;
import com.example.mibocatafx.util.HibernateUtil;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PedidoDao {

    public List<Pedido> obtenerPedidosPorFecha(Date fecha, int paginaActual, int pedidosPorPagina, Bocadillo.Tipo tipoFiltro) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String consulta = "FROM Pedido p WHERE p.fecha >= :fecha AND p.retirado IS NULL";
            if (tipoFiltro != null) {
                consulta += " AND p.bocadillo.tipo = :tipoBocadillo";
            }
            var query = session.createQuery(consulta, Pedido.class)
                    .setParameter("fecha", fecha)
                    .setFirstResult((paginaActual - 1) * pedidosPorPagina)
                    .setMaxResults(pedidosPorPagina);

            // Si se está filtrando por tipo de bocadillo, añadir el parámetro
            if (tipoFiltro != null) {
                query.setParameter("tipoBocadillo", tipoFiltro);
            }

            return query.list();
        }
    }

    public List<Pedido> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Pedido", Pedido.class).getResultList();
        }
    }

    public List<Pedido> getPaginated(int page, int offset, HashMap<String, String> filtros) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("FROM Pedido p WHERE true");

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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("SELECT COUNT(p) FROM Pedido p WHERE true");

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

    public List<Pedido> getPedidoAlumno(Alumno idAlumno, Date fecha) {
        LocalDate fechaSinHora = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Asegúrate de que la consulta traiga también el bocadillo relacionado
            TypedQuery<Pedido> query = session.createQuery(
                    "FROM Pedido p JOIN FETCH p.bocadillo WHERE p.alumno = :idAlumno AND CAST(p.fecha AS localdate) = :fecha",
                    Pedido.class
            );
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
        }
    }


    public int obtenerTotalPedidos (Date fecha, Bocadillo.Tipo tipoFiltro){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String consulta = "SELECT COUNT(p) FROM Pedido p WHERE p.fecha >= :fecha AND p.retirado IS NULL";

            // Si hay un tipo de bocadillo seleccionado, añadirlo a la consulta
            if (tipoFiltro != null) {
                consulta += " AND p.bocadillo.tipo = :tipoBocadillo";
            }

            var query = session.createQuery(consulta)
                    .setParameter("fecha", fecha);

            // Si se está filtrando por tipo de bocadillo, añadir el parámetro
            if (tipoFiltro != null) {
                query.setParameter("tipoBocadillo", tipoFiltro);
            }

            return ((Long) query.uniqueResult()).intValue();
        }
    }

    public void actualizarPedido (Pedido pedido){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(pedido);
            transaction.commit();
        }
    }

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


    public Alumno obtenerAlumno(Usuario usuario) {
        Alumno alumno = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Alumno> resultado = session.createQuery("FROM Alumno WHERE id = :idUsuario", Alumno.class)
                    .setParameter("idUsuario", usuario.getId())
                    .getResultList();

            if (!resultado.isEmpty()) {
                alumno = resultado.get(0);
            } else {
                System.out.println("No se encontró el alumno con id: " + usuario.getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return alumno;
    }

    /*
    *
    * Método para la páginación del historial
     */
    public List<Pedido> getPaginatedHistorial(int page, int offset, Alumno alumno) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Pedido p WHERE p.retirado IS NULL AND p.alumno = :alumno";  // Filtro por alumno
            Query<Pedido> query = session.createQuery(hql, Pedido.class)
                    .setParameter("alumno", alumno)  // Pasar el alumno como parámetro
                    .setFirstResult((page - 1) * offset)  // Página actual
                    .setMaxResults(offset);  // Número de resultados por página

            return query.list();  // Devolver los pedidos correspondientes a la página
        }
    }

    /*
     *
     * Método para la páginación del historial
     */
    public long coutHistorial(HashMap<String, String> filtros, Alumno alumno) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("SELECT COUNT(p) FROM Pedido p WHERE p.alumno = :alumno");

            if (filtros != null) {
                for (String key : filtros.keySet()) {
                    if (key.equals("tipo")) {
                        hql.append(" AND p.bocadillo.tipo LIKE :").append(key);
                    } else {
                        hql.append(" AND p.").append(key).append(" LIKE :").append(key);
                    }
                }
            }

            Query<Long> query = session.createQuery(hql.toString(), Long.class).setParameter("alumno", alumno);  // Pasar el alumno como parámetro

            // Asignar valores a los parámetros de la consulta
            if (filtros != null) {
                for (HashMap.Entry<String, String> filtro : filtros.entrySet()) {
                    query.setParameter(filtro.getKey(), "%" + filtro.getValue() + "%");
                }
            }

            return query.getSingleResult();
        }
    }

    /*
     *
     * Método para obtener el total de pedidos del alumno logueado
     */
    public int obtenerTotalPedidosAlumno(Alumno alumno) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String consulta = "SELECT COUNT(p) FROM Pedido p WHERE p.retirado IS NULL AND p.alumno = :alumno";
            Query<Long> query = session.createQuery(consulta, Long.class)
                    .setParameter("alumno", alumno);  // Pasar el alumno como parámetro

            return ((Long) query.uniqueResult()).intValue();  // Devuelve el total de pedidos del alumno logueado
        }
    }


    public long obtenerPedidosFriosDeHoy() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Obtener la fecha de hoy sin hora
            LocalDate hoyLocalDate = LocalDate.now();
            Date hoy = Date.from(hoyLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Consulta para contar los pedidos fríos de hoy
            String consulta = "SELECT COUNT(p) FROM Pedido p WHERE p.bocadillo.tipo = 'Frio' AND DATE(p.fecha) = :hoy";
            Query<Long> query = session.createQuery(consulta, Long.class);
            query.setParameter("hoy", hoy);

            Long resultado = query.uniqueResult();

            // Retornar el total de pedidos fríos de hoy
            return (resultado != null) ? resultado : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public long obtenerPedidosCalientesDeHoy() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Obtener la fecha de hoy sin hora
            LocalDate hoyLocalDate = LocalDate.now();
            Date hoy = Date.from(hoyLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Consulta para contar los pedidos fríos de hoy
            String consulta = "SELECT COUNT(p) FROM Pedido p WHERE p.bocadillo.tipo = 'Caliente' AND DATE(p.fecha) = :hoy";
            Query<Long> query = session.createQuery(consulta, Long.class);
            query.setParameter("hoy", hoy);

            Long resultado = query.uniqueResult();

            // Retornar el total de pedidos fríos de hoy
            return (resultado != null) ? resultado : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}