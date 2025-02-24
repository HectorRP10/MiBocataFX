package com.example.mibocatafx.service;

import com.example.mibocatafx.UsuarioSesion;
import com.example.mibocatafx.controller.DashboardAlumnoController;
import com.example.mibocatafx.dao.PedidoDao;
import com.example.mibocatafx.models.Alumno;
import com.example.mibocatafx.models.Bocadillo;
import com.example.mibocatafx.models.Pedido;
import com.example.mibocatafx.models.Usuario;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class PedidoService {

    public void insertarPedido(Pedido pedido) {
        if (pedido != null) {
            pedidoDao.save(pedido);
        } else {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }
    }
    private PedidoDao pedidoDao;

    public PedidoService() {
        this.pedidoDao = new PedidoDao();
    }

    public List<Pedido> obtenerPedidosPorFecha(Date fecha, int paginaActual, int pedidosPorPagina, Bocadillo.Tipo tipoFiltro) {
        return pedidoDao.obtenerPedidosPorFecha(fecha, paginaActual, pedidosPorPagina, tipoFiltro);
    }


    public List<Pedido> getPaginated(int page, int offset, HashMap<String, String> filtros) {
        return pedidoDao.getPaginated(page, offset, filtros);
    }

    public long cout(HashMap<String, String> filtros) {
        return pedidoDao.cout(filtros);
    }





    public List<Pedido> getPaginatedHistorial(int page, int offset) {
        Alumno alumno = obtenerAlumno(UsuarioSesion.obtenerUsuarioActual());  // Obtener el alumno logueado
        return pedidoDao.getPaginatedHistorial(page, offset, alumno);
    }



    public long coutHistorial(HashMap<String, String> filtros) {
        Alumno alumno = obtenerAlumno(UsuarioSesion.obtenerUsuarioActual());  // Obtener el alumno logueado
        return pedidoDao.coutHistorial(filtros, alumno);
    }

    /**
     *
     * Método para obtener el pedido segun el id_almno y la fecha del pedido
     */
    public List<Pedido> getPedidoAlumno(Alumno idAlumno,  Date fecha) {
        return pedidoDao.getPedidoAlumno(idAlumno, fecha);
    }

    public void eliminarPedido(Pedido pedido) {
        pedidoDao.delete(pedido);
    }


    /**
     *
     * Este método gestiona pedidos segun lo que haga usuario
     */
    public  void gestionarPedido(Bocadillo bocadillo, HBox bocadilloBox, List<HBox> listaBocadillos) {
        if (bocadillo != null) {
            LocalDate fechaActual = LocalDate.now();
            Date fecha = Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant());

            List<Pedido> pedidos = getPedidoAlumno(obtenerAlumno(UsuarioSesion.obtenerUsuarioActual()), fecha);

            if (!pedidos.isEmpty()) {
                Pedido pedidoAnterior = pedidos.get(0);

                if (pedidoAnterior.getBocadillo().getId() == bocadillo.getId()) {
                    //Eliminar el pedido si es el mismo bocadillo
                    eliminarPedido(pedidoAnterior);

                } else {
                    //Actualizar el pedido si el bocadillo es diferente
                    pedidoAnterior.setBocadillo(bocadillo);
                    pedidoAnterior.setPrecio(bocadillo.getPrecio());
                    actualizarPedido(pedidoAnterior);

                }
            } else {
                //Insertar un nuevo pedido si no hay uno existente
                Pedido nuevoPedido = new Pedido();
                nuevoPedido.setAlumno(obtenerAlumno(UsuarioSesion.obtenerUsuarioActual()));

                nuevoPedido.setBocadillo(bocadillo);
                nuevoPedido.setFecha(fecha);
                nuevoPedido.setPrecio(bocadillo.getPrecio());
                nuevoPedido.setId_descuento(null);
                insertarPedido(nuevoPedido);


            }

        }
    }


    public int obtenerTotalPedidos(Date fecha, Bocadillo.Tipo tipoFiltro) {
        return pedidoDao.obtenerTotalPedidos(fecha, tipoFiltro);
    }

    public int obtenerTotalPedidosAlumno() {
        Alumno alumno = obtenerAlumno(UsuarioSesion.obtenerUsuarioActual());  // Obtener el alumno logueado
        return pedidoDao.obtenerTotalPedidosAlumno(alumno);
    }


    public void actualizarPedido(Pedido pedido) {
        pedidoDao.actualizarPedido(pedido);
    }


    public Alumno obtenerAlumno(Usuario usuario){

        return pedidoDao.obtenerAlumno(usuario);
    }

    public long obtenerPedidosFriosDeHoy() {
        return pedidoDao.obtenerPedidosFriosDeHoy();
    }
    public long obtenerPedidosCalientesDeHoy() {
        return pedidoDao.obtenerPedidosCalientesDeHoy();
    }

}