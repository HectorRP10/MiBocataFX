package com.example.mibocatafx.service;

import com.example.mibocatafx.UsuarioSesion;
import com.example.mibocatafx.dao.BocadilloDao;
import com.example.mibocatafx.models.Bocadillo;
import com.example.mibocatafx.models.Pedido;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class BocadilloService {

    private final BocadilloDao bocadilloDao = new BocadilloDao();
    private final PedidoService pedidoService = new PedidoService();

    public void save(Bocadillo bocadillo) {
        if (bocadillo.getId() == null || bocadillo.getId() == 0) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }
        bocadilloDao.save(bocadillo);
    }

    public List<Bocadillo> getAll() {
        return bocadilloDao.getAll();
    }

    public List<Bocadillo> getPaginated() {
        return bocadilloDao.getPaginated();
    }

    public List<Bocadillo> getByDiaSemana(String diaSemana) {
        return bocadilloDao.getByDiaSemana(diaSemana);
    }

    public String obtenerAbreviaturaDia(DayOfWeek dia) {
        switch (dia) {
            case MONDAY: return "L";
            case TUESDAY: return "M";
            case WEDNESDAY: return "X";
            case THURSDAY: return "J";
            case FRIDAY: return "V";
            case SATURDAY: return "S";
            case SUNDAY: return "D";
            default: return "";
        }
    }

    private void insertarPedido(Bocadillo bocadillo) {
        if (bocadillo != null) {
            Pedido nuevoPedido = new Pedido();
            nuevoPedido.setId_alumno(UsuarioSesion.obtenerUsuarioActual().getId());
            nuevoPedido.setId_bocadillo(bocadillo.getId());
            nuevoPedido.setFecha(new Date());
            nuevoPedido.setPrecio(bocadillo.getPrecio());
            nuevoPedido.setId_descuento(null);
            pedidoService.insertarPedido(nuevoPedido);
        }
    }

    public void cargarBocadillos(HBox bocadilloContainer) {
        DayOfWeek diaActual = LocalDate.now().getDayOfWeek();
        String diaSemana = obtenerAbreviaturaDia(diaActual);
        List<Bocadillo> bocadillos = getByDiaSemana(diaSemana);

        bocadilloContainer.getChildren().clear();

        for (Bocadillo bocadillo : bocadillos) {
            HBox bocadilloBox = new HBox(10);
            bocadilloBox.setStyle("-fx-padding: 50px; -fx-border-color: black; -fx-border-radius: 5px; -fx-background-color: #f9f9f9;");

            if (bocadillo.getTipo().equals("frio")) {
                bocadilloBox.setStyle(bocadilloBox.getStyle() + "-fx-background-color: #89E9A8;");
            } else if (bocadillo.getTipo().equals("caliente")) {
                bocadilloBox.setStyle(bocadilloBox.getStyle() + "-fx-background-color: #F25F5F;");
            }

            bocadilloBox.setUserData(bocadillo);
            bocadilloBox.setOnMouseClicked(event -> {
                Bocadillo seleccionado = (Bocadillo) bocadilloBox.getUserData();
                System.out.println("Bocadillo seleccionado: " + seleccionado.getNombre());
                insertarPedido(seleccionado);
            });

            Label nombreLabel = new Label(bocadillo.getNombre());
            nombreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 25));
            nombreLabel.setTextFill(Color.BLACK);

            Label ingredientesLabel = new Label("Ingredientes: " + bocadillo.getIngredientes());
            ingredientesLabel.setFont(Font.font("Arial", 17));
            ingredientesLabel.setTextFill(Color.BLACK);

            Label precioLabel = new Label("Precio: " + bocadillo.getPrecio() + "€");
            precioLabel.setFont(Font.font("Arial", FontWeight.BOLD, 17));
            precioLabel.setTextFill(Color.DARKBLUE);

            VBox textoBox = new VBox(5, nombreLabel, ingredientesLabel, precioLabel);
            bocadilloBox.getChildren().addAll(textoBox);
            bocadilloContainer.getChildren().addAll(bocadilloBox, new Separator());
        }
    }
}
