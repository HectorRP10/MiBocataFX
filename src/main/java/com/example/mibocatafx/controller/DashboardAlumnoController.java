package com.example.mibocatafx.controller;

import com.example.mibocatafx.UsuarioSesion;
import com.example.mibocatafx.models.Bocadillo;
import com.example.mibocatafx.models.Usuario;
import com.example.mibocatafx.service.BocadilloService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class DashboardAlumnoController implements Initializable {

    private final BocadilloService bocadilloService = new BocadilloService();

    @FXML
    private HBox bocadilloContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarBocadillos();  // Cargar los bocadillos

    }

    public void cargarBocadillos() {
        // Obtener el día actual en formato abreviado
        DayOfWeek diaActual = LocalDate.now().getDayOfWeek();
        String diaSemana = obtenerAbreviaturaDia(diaActual);

        // Obtener los bocadillos del día actual
        List<Bocadillo> bocadillos = bocadilloService.getByDiaSemana(diaSemana);

        // Limpiar el contenedor antes de agregar nuevos elementos
        bocadilloContainer.getChildren().clear();

        for (Bocadillo bocadillo : bocadillos) {
            // Crear contenedor horizontal
            HBox bocadilloBox = new HBox(10);
            bocadilloBox.setStyle("-fx-padding: 50px; -fx-border-color: black; -fx-border-radius: 5px; -fx-background-color: #f9f9f9;");

            // Asignar color de fondo según el tipo(caliente/frio)
            if (bocadillo.getTipo().equals("frio")) {
                bocadilloBox.setStyle(bocadilloBox.getStyle() + "-fx-background-color: #89E9A8;");
            } else if (bocadillo.getTipo().equals("caliente")) {
                bocadilloBox.setStyle(bocadilloBox.getStyle() + "-fx-background-color: #F25F5F;");
            }

            // Nombre del bocadillo
            Label nombreLabel = new Label(bocadillo.getNombre());
            nombreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 25));
            nombreLabel.setTextFill(Color.BLACK);

            // Ingredientes del bocadillo
            Label ingredientesLabel = new Label("Ingredientes: " + bocadillo.getIngredientes());
            ingredientesLabel.setFont(Font.font("Arial", 17));
            ingredientesLabel.setTextFill(Color.BLACK);

            // Precio del bocadillo
            Label precioLabel = new Label("Precio: " + bocadillo.getPrecio() +"€");
            precioLabel.setFont(Font.font("Arial",FontWeight.BOLD, 17));
            precioLabel.setTextFill(Color.DARKBLUE);

            // Contenedor vertical para organizar texto
            VBox textoBox = new VBox(5, nombreLabel, ingredientesLabel,precioLabel);

            // Agregar elementos al HBox
            bocadilloBox.getChildren().addAll(textoBox);

            // Agregar la tarjeta al contenedor principal
            bocadilloContainer.getChildren().addAll(bocadilloBox, new Separator());
        }
    }

    private String obtenerAbreviaturaDia(DayOfWeek dia) {
        switch (dia) {
            case MONDAY: return "L";
            case TUESDAY: return "M";
            case WEDNESDAY: return "X";
            case THURSDAY: return "J";
            case FRIDAY: return "V";
            default: return "";
        }
    }
}