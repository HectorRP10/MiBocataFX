package com.example.mibocatafx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class HistorialController implements Initializable {

    @FXML
    private Label labelHistorial;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Historial de bocadillos cargado.");
    }
}
