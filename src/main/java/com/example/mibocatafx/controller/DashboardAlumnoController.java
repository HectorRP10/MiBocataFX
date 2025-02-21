package com.example.mibocatafx.controller;

import com.example.mibocatafx.MainApplication;
import com.example.mibocatafx.UsuarioSesion;
import com.example.mibocatafx.service.BocadilloService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardAlumnoController implements Initializable {

    private final BocadilloService bocadilloService = new BocadilloService();

    @FXML
    private HBox bocadilloContainer;

    @FXML
    private BorderPane borderPane;
    private Stage stage;

    @FXML
    private AnchorPane contentPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Llamar al servicio para cargar los bocadillos
        if (bocadilloContainer != null) {
            bocadilloService.cargarBocadillos(bocadilloContainer);
        } else {
            System.out.println("bocadilloContainer es null");
        }
    }

    /*
    *
    * Método para cargar el center directamente cuando inicio sesion
     */
    public  void cargarCenter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mibocatafx/fxml/DashboardAlumno.fxml"));
            AnchorPane centerContent = loader.load();
            borderPane.setCenter(centerContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnHistorialPedidos(ActionEvent event)throws IOException{
        cargarVista("/com/example/mibocatafx/fxml/HistorialBocadillo.fxml");

    }

    @FXML
    private void btnHome(ActionEvent event)throws IOException{
        cargarVista("/com/example/mibocatafx/fxml/DashboardAlumno.fxml");

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
}