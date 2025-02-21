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
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class BocadilloService {

    private final BocadilloDao bocadilloDao = new BocadilloDao();
    private final PedidoService pedidoService = new PedidoService();

    public void save(Bocadillo bocadillo) {
        // Validación antes de guardar
        if ( bocadillo.getId() == 0) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }
        bocadilloDao.save(bocadillo);
    }

    public List<Bocadillo> getAll() {
        return bocadilloDao.getAll();
    }

    public List<Bocadillo> getPaginated(int paginaActual, int bocadilloPorPagina) {
        return bocadilloDao.getPaginated(paginaActual, bocadilloPorPagina);
    }

    /**
     *
     * Este método obtiene los bocadillos según el la abreviatura
     */
    public List<Bocadillo> getBocadilloDia(String diaSemana) {
        return bocadilloDao.getBocadilloDia(diaSemana);
    }

    /**
     *
     * Este método devuelve la abreviatra como esta en la BD
     */
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

    /**
     *
     * Este método muestra por pantalla los bocadillos del dia
     */
    public void cargarBocadillos(HBox bocadilloContainer) {
        DayOfWeek diaActual = LocalDate.now().getDayOfWeek();
        String diaSemana = obtenerAbreviaturaDia(diaActual);
        List<Bocadillo> bocadillos = getBocadilloDia(diaSemana);

        bocadilloContainer.getChildren().clear();

        // Lista para almacenar todos los bocadillos en pantalla
        List<HBox> listaBocadillos = new ArrayList<>();

        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
        Date fecha = Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant());
        // Buscar los pedidos del alumno
        List<Pedido> pedidos = pedidoService.getPedidoAlumno(pedidoService.obtenerAlumno(UsuarioSesion.obtenerUsuarioActual()), fecha);

        for (Bocadillo bocadillo : bocadillos) {
            HBox bocadilloBox = new HBox(10);
            bocadilloBox.setStyle("-fx-padding: 50px; -fx-border-color: black; -fx-border-radius: 5px; -fx-background-color: #f9f9f9;");

            // Verificar si ya existe un pedido para este bocadillo
            boolean existePedidoParaBocadillo = pedidos.stream().anyMatch(pedido -> pedido.getBocadillo().getId() == bocadillo.getId());

            //Asignar el color al iniciar app
            if (bocadillo.getTipo().equals("Frio")) {
                bocadilloBox.setStyle(bocadilloBox.getStyle() + "-fx-background-color: #89E9A8;"); // Verde
            } else if (bocadillo.getTipo().equals("Caliente")) {
                bocadilloBox.setStyle(bocadilloBox.getStyle() + "-fx-background-color: #F25F5F;"); // Rojo
            }

            bocadilloBox.setUserData(bocadillo);
            bocadilloBox.setOnMouseClicked(event -> {
                Bocadillo seleccionado = (Bocadillo) bocadilloBox.getUserData();
                System.out.println("Bocadillo seleccionado: " + seleccionado.getNombre());
                pedidoService.gestionarPedido(seleccionado, bocadilloBox, listaBocadillos); // Actualizar el color dinámicamente
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

            // Añadir el HBox a la lista
            listaBocadillos.add(bocadilloBox);
        }
    }

    public void modificar(Bocadillo bocadillo) {
        bocadilloDao.update(bocadillo);
    }
    public void eliminar(Bocadillo bocadillo) {
        bocadilloDao.delete(bocadillo);
    }

    public int obtenerTotalBocadillos() {
        return bocadilloDao.obtenerTotalBocadillos();
    }
}