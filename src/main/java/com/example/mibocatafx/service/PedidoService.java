package com.example.mibocatafx.service;

import com.example.mibocatafx.dao.PedidoDao;
import com.example.mibocatafx.models.Pedido;

import java.util.List;

public class PedidoService {
    private final PedidoDao pedidoDao = new PedidoDao();

    public void save(Pedido pedido) {
        // Validación antes de guardar
        if (pedido.getId() == null || pedido.getId() == 0) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }

        pedidoDao.save(pedido);
    }

    public List<Pedido> getAll() {
        return pedidoDao.getAll();
    }

    public List<Pedido> getPaginated() {
        return pedidoDao.getPaginated();
    }

}
