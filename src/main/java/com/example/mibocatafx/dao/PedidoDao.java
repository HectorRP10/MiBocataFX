package com.example.mibocatafx.dao;

import com.example.mibocatafx.models.Bocadillo;
import com.example.mibocatafx.models.Pedido;
import com.example.mibocatafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    public int obtenerTotalPedidos(Date fecha, Bocadillo.Tipo tipoFiltro) {
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

    public void actualizarPedido(Pedido pedido) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(pedido);
            transaction.commit();
        }
    }
}
