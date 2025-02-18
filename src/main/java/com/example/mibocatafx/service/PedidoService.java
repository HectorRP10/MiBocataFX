package com.example.mibocatafx.service;

import com.example.mibocatafx.dao.PedidoDao;
import com.example.mibocatafx.models.Bocadillo;
import com.example.mibocatafx.models.Pedido;

import java.util.Date;
import java.util.List;

public class PedidoService {

    private PedidoDao pedidoDao;

    public PedidoService() {
        this.pedidoDao = new PedidoDao();
    }

    public List<Pedido> obtenerPedidosPorFecha(Date fecha, int paginaActual, int pedidosPorPagina, Bocadillo.Tipo tipoFiltro) {
        return pedidoDao.obtenerPedidosPorFecha(fecha, paginaActual, pedidosPorPagina, tipoFiltro);
    }

    public int obtenerTotalPedidos(Date fecha, Bocadillo.Tipo tipoFiltro) {
        return pedidoDao.obtenerTotalPedidos(fecha, tipoFiltro);
    }

    public void actualizarPedido(Pedido pedido) {
        pedidoDao.actualizarPedido(pedido);
    }
}
