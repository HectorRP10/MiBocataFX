package com.example.mibocatafx.controller;

import com.example.mibocatafx.MainApplication;
import com.example.mibocatafx.UsuarioSesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdministradorController {

    @FXML
    private BorderPane borderPane;
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

    private void cargarVista(String ruta) throws IOException {
        // Cargar una nueva vista y ponerla en el centro
        AnchorPane nuevaVista = FXMLLoader.load(getClass().getResource(ruta));
        borderPane.setCenter(nuevaVista);

        if (borderPane.getScene() != null) {
            stage = (Stage) borderPane.getScene().getWindow();
            ajustarDimensionesVentana(nuevaVista);

            // Forzar que la ventana se mantenga maximizada
            stage.setMaximized(true);
        } else {
            System.out.println("La escena no está disponible aún.");
        }
    }

    private void ajustarDimensionesVentana(AnchorPane view) {
        if (stage != null) {
            // Verificar si la ventana está maximizada
            if (!stage.isMaximized()) {
                double width = view.getPrefWidth();
                double height = view.getPrefHeight();

                stage.setWidth(width + 150);  // Agregar margen
                stage.setHeight(height + 60);
            }
        } else {
            System.out.println("El stage es nulo, no se puede ajustar el tamaño de la ventana.");
        }
    }


    @FXML
    private void btnCerrarSesion(ActionEvent event)throws IOException{
        UsuarioSesion.cerrarSesion();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

        // Cerrar la ventana de login
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }


}