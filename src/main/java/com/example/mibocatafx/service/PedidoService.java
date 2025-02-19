package com.example.mibocatafx.service;

import com.example.mibocatafx.UsuarioSesion;
import com.example.mibocatafx.dao.PedidoDao;
import com.example.mibocatafx.models.Bocadillo;
import com.example.mibocatafx.models.Pedido;
import javafx.scene.layout.HBox;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PedidoService {
    private final PedidoDao pedidoDao = new PedidoDao();

    public void insertarPedido(Pedido pedido) {
        if (pedido != null) {
            pedidoDao.save(pedido);
        } else {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }
    }

    public List<Pedido> getAll() {
        return pedidoDao.getAll();
    }

    public List<Pedido> getPaginated(int page, int offset, HashMap<String, String> filtros) {
        return pedidoDao.getPaginated(page, offset, filtros);
    }

    public long cout(HashMap<String, String> filtros) {
        return pedidoDao.cout(filtros);
    }

    /**
     *
     * Método para obtener el pedido segun el id_almno y la fecha del pedido
     */
    public List<Pedido> getPedidoAlumno(int idAlumno,  Date fecha) {
        return pedidoDao.getPedidoAlumno(idAlumno, fecha);
    }

    public void eliminarPedido(Pedido pedido) {
        pedidoDao.delete(pedido);
    }

    public void actualizarPedido(Pedido pedido) {
        if (pedido != null) {
            pedidoDao.update(pedido);
        } else {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }
    }

    /**
     *
     * Este método gestiona pedidos segun lo que haga usuario
     */
    public  void gestionarPedido(Bocadillo bocadillo, HBox bocadilloBox, List<HBox> listaBocadillos) {
        if (bocadillo != null) {
            LocalDate fechaActual = LocalDate.now();
            Date fecha = Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant());

            List<Pedido> pedidos = getPedidoAlumno(UsuarioSesion.obtenerUsuarioActual().getId(), fecha);
            String estiloBase = "-fx-padding: 50px; -fx-border-color: black; -fx-border-radius: 5px; -fx-background-color: ";

            // Restaurar color de todos los bocadillos
            for (HBox box : listaBocadillos) {
                Bocadillo boc = (Bocadillo) box.getUserData();
                restaurarColorBocadillo(box, boc);
            }

            if (!pedidos.isEmpty()) {
                Pedido pedidoAnterior = pedidos.get(0);

                if (pedidoAnterior.getId_bocadillo() == bocadillo.getId()) {
                    //Eliminar el pedido si es el mismo bocadillo
                    eliminarPedido(pedidoAnterior);
                    restaurarColorBocadillo(bocadilloBox, bocadillo);
                    return;
                } else {
                    //Actualizar el pedido si el bocadillo es diferente
                    pedidoAnterior.setId_bocadillo(bocadillo.getId());
                    pedidoAnterior.setPrecio(bocadillo.getPrecio());
                    actualizarPedido(pedidoAnterior);
                }
            } else {
                //Insertar un nuevo pedido si no hay uno existente
                Pedido nuevoPedido = new Pedido();
                nuevoPedido.setId_alumno(UsuarioSesion.obtenerUsuarioActual().getId());
                nuevoPedido.setId_bocadillo(bocadillo.getId());
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
    private void restaurarColorBocadillo(HBox bocadilloBox, Bocadillo bocadillo) {
        String estiloBase = "-fx-padding: 50px; -fx-border-color: black; -fx-border-radius: 5px; -fx-background-color: ";
        if (bocadillo.getTipo().equals("frio")) {
            bocadilloBox.setStyle(estiloBase + "#89E9A8;"); // Verde
        } else if (bocadillo.getTipo().equals("caliente")) {
            bocadilloBox.setStyle(estiloBase + "#F25F5F;"); // Rojo
        }
    }
}