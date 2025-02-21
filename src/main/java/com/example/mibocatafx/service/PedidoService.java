package com.example.mibocatafx.service;

import com.example.mibocatafx.UsuarioSesion;
import com.example.mibocatafx.dao.PedidoDao;
import com.example.mibocatafx.models.Alumno;
import com.example.mibocatafx.models.Bocadillo;
import com.example.mibocatafx.models.Pedido;
import com.example.mibocatafx.models.Usuario;
import javafx.scene.layout.HBox;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import java.util.Date;
import java.util.List;

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

    public List<Pedido> obtenerPedidosPorUsuario(Alumno alumno) {
        return pedidoDao.obtenerPedidosPorUsuario(alumno);
    }

    public List<Pedido> getPaginated(int page, int offset, HashMap<String, String> filtros) {
        return pedidoDao.getPaginated(page, offset, filtros);
    }

    public long cout(HashMap<String, String> filtros) {
        return pedidoDao.cout(filtros);
    }





    public List<Pedido> getPaginated(int page, int offset) {
        return pedidoDao.getPaginatedHistorial(page, offset);  // Llamada sin filtros
    }


    public long coutHistorial(HashMap<String, String> filtros) {
        return pedidoDao.cout(filtros);
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
            String estiloBase = "-fx-padding: 50px; -fx-border-color: black; -fx-border-radius: 5px; -fx-background-color: ";

            // Restaurar color de todos los bocadillos
            for (HBox box : listaBocadillos) {
                Bocadillo boc = (Bocadillo) box.getUserData();
                restaurarColorBocadillo(box, boc);
            }

            if (!pedidos.isEmpty()) {
                Pedido pedidoAnterior = pedidos.get(0);

                if (pedidoAnterior.getBocadillo().getId() == bocadillo.getId()) {
                    //Eliminar el pedido si es el mismo bocadillo
                    eliminarPedido(pedidoAnterior);
                    restaurarColorBocadillo(bocadilloBox, bocadillo);
                    return;
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
               //nuevoPedido.setAlumno(UsuarioSesion.obtenerUsuarioActual());

                nuevoPedido.setBocadillo(bocadillo);
                nuevoPedido.setFecha(fecha);
                nuevoPedido.setPrecio(bocadillo.getPrecio());
                nuevoPedido.setId_descuento(null);
                insertarPedido(nuevoPedido);

            }
            // Cambiar color del bocadillo seleccionado a beige
            bocadilloBox.setStyle(estiloBase + "#FFDDC1;");
        }
    }

    /**
     * Método para restaurar el color del bocadillo según su tipo.
     */
    public void restaurarColorBocadillo(HBox bocadilloBox, Bocadillo bocadillo) {
        String estiloBase = "-fx-padding: 50px; -fx-border-color: black; -fx-border-radius: 5px; -fx-background-color: ";
        if (bocadillo.getTipo().equals("Frio")) {
            bocadilloBox.setStyle(estiloBase + "#89E9A8;"); // Verde
        } else if (bocadillo.getTipo().equals("Caliente")) {
            bocadilloBox.setStyle(estiloBase + "#F25F5F;"); // Rojo
        }
    }

    public int obtenerTotalPedidos(Date fecha, Bocadillo.Tipo tipoFiltro) {
        return pedidoDao.obtenerTotalPedidos(fecha, tipoFiltro);
    }

    public int obtenerTotalPedidosAlumno() {
        return pedidoDao.obtenerTotalPedidosAlumno();
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
        return pedidoDao.obtenerPedidosFriosDeHoy();
    }







}
