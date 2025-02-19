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
        AnchorPane.setTopAnchor(view, 0.0);
        AnchorPane.setLeftAnchor(view, 0.0);
        AnchorPane.setBottomAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);

        borderPane.setCenter(view);

        /*
        BorderPane view1= FXMLLoader.load(getClass().getResource("/com/example/mibocatafx/fxml/nav_alumno.fxml"));
        borderPane.setTop(view1);

         */
    }

    @FXML
    private void btnHome(ActionEvent event)throws IOException{
        BorderPane view= FXMLLoader.load(getClass().getResource("/com/example/mibocatafx/fxml/DashboardAlumno.fxml"));
        borderPane.setCenter(view);

        BorderPane view1= FXMLLoader.load(getClass().getResource("/com/example/mibocatafx/fxml/nav_alumno.fxml"));
        borderPane.setTop(view1);
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