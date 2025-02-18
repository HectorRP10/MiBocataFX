package com.example.mibocatafx.controller;

import com.example.mibocatafx.service.BocadilloService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardAlumnoController implements Initializable {

    private final BocadilloService bocadilloService = new BocadilloService();

    @FXML
    private HBox bocadilloContainer;

    @FXML
    private BorderPane borderPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Llamar al servicio para cargar los bocadillos
        if (bocadilloContainer != null) {
            bocadilloService.cargarBocadillos(bocadilloContainer);
        } else {
            System.out.println("bocadilloContainer es null");
        }
    }
    @FXML
    private void btnHistorialPedidos(ActionEvent event)throws IOException{
        AnchorPane view= FXMLLoader.load(getClass().getResource("/com/example/mibocatafx/fxml/HistorialBocadillo.fxml"));
        borderPane.setCenter(view);
    }
}