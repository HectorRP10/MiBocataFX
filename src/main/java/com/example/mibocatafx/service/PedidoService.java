package com.example.mibocatafx.service;

import com.example.mibocatafx.dao.PedidoDao;
import com.example.mibocatafx.models.Pedido;

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

    public List<Pedido> getPaginated() {
        return pedidoDao.getPaginated();
    }
}

