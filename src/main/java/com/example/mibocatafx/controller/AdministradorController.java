package com.example.mibocatafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdministradorController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button alergenos;

    @FXML
    private Button bocadillos;

    @FXML
    private Button cursos;

    @FXML
    private Button descuentos;

    @FXML
    private Button usuarios;



    private Stage stage;

    @FXML
    private void initialize() {

    }

    @FXML
    private void gestionBocadillos(ActionEvent event) throws IOException {
        cargarVista("/com/example/mibocatafx/fxml/AdminBocadillos.fxml");
    }

    @FXML
    private void gestionUsuarios(ActionEvent event) throws IOException {
        cargarVista("/com/example/mibocatafx/fxml/AdminUsuarios.fxml");
    }

    @FXML
    private void gestionDescuentos(ActionEvent event) throws IOException {
        cargarVista("/com/example/mibocatafx/fxml/AdminDescuentos.fxml");
    }

    @FXML
    private void gestionAlergenos(ActionEvent event) throws IOException {
        cargarVista("/com/example/mibocatafx/fxml/AdminAlergenos.fxml");
    }

    @FXML
    private void gestionCursos(ActionEvent event) throws IOException {
        cargarVista("/com/example/mibocatafx/fxml/AdminCursos.fxml");
    }

    private void cargarVista(String rutafxml) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource(rutafxml));
        borderPane.setCenter(view);

        if (borderPane.getScene() != null) {
            stage = (Stage) borderPane.getScene().getWindow();
            ajustarDimensionesVentana(view);
        } else {
            System.out.println("La escena no está disponible aún.");
        }
    }

    private void ajustarDimensionesVentana(AnchorPane view) {
        if (stage != null) {
            double width = view.getPrefWidth();
            double height = view.getPrefHeight();

            stage.setWidth(width + 150);  // Agregar margen
            stage.setHeight(height + 60);
        } else {
            System.out.println("El stage es nulo, no se puede ajustar el tamaño de la ventana.");
        }
    }
}
