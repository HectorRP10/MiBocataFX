package com.example.mibocatafx.controller;

import com.example.mibocatafx.MainApplication;
import com.example.mibocatafx.UsuarioSesion;
import com.example.mibocatafx.models.Alumno;
import com.example.mibocatafx.models.Bocadillo;
import com.example.mibocatafx.models.Pedido;
import com.example.mibocatafx.service.BocadilloService;
import com.example.mibocatafx.service.PedidoService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DashboardAlumnoController implements Initializable {
    private final PedidoService pedidoService = new PedidoService();

    private final BocadilloService bocadilloService = new BocadilloService();

    @FXML
    private HBox bocadilloContainer;

    @FXML
    private BorderPane borderPane;
    private Stage stage;

    @FXML
    private Label mensajePedido;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private String ultimoMensaje = "";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Llamar al servicio para cargar los bocadillos
        if (bocadilloContainer != null) {
            bocadilloService.cargarBocadillos(bocadilloContainer);
        } else {
            System.out.println("bocadilloContainer es null");
        }
        cargarMensajePedido();
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


    private void cargarMensajePedido() {
            try {
                Alumno alumno = new Alumno();
                alumno.setId(1);
                Date fechaActual = new Date();

                List<Pedido> pedidos = pedidoService.getPedidoAlumno(alumno, fechaActual);

                String nuevoMensaje = pedidos.isEmpty()
                        ? "No hay pedidos para hoy."
                        : "Pedido reservado: " + pedidos.get(0).getBocadillo().getNombre();

                // Solo actualizar si el mensaje es diferente
                if (!nuevoMensaje.equals(ultimoMensaje)) {
                    ultimoMensaje = nuevoMensaje;
                    Platform.runLater(() -> {
                        if (mensajePedido != null) {
                            mensajePedido.setText(nuevoMensaje);
                        } else {
                            System.out.println("mensajePedido es nulo");
                        }
                    });
                }
            } catch (Exception e) {
                Platform.runLater(() -> {
                    if (mensajePedido != null) {
                        mensajePedido.setText("Error al cargar el pedido.");
                    }
                });
                e.printStackTrace();
            }

    }

}