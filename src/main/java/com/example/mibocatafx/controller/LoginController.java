package com.example.mibocatafx.controller;
import com.example.mibocatafx.MainApplication;
import com.example.mibocatafx.UsuarioSesion;
import com.example.mibocatafx.dao.UsuarioDao;
import com.example.mibocatafx.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void onLoginButtonClick(ActionEvent event) throws IOException {
        String email = usernameField.getText();
        String password = passwordField.getText();

        UsuarioDao usuarioDao = new UsuarioDao();
        Usuario usuario = usuarioDao.validar_login(email, password);

        if (usuario != null) {
            UsuarioSesion.iniciarSesion(usuario); // Se guarda el usuario en sesión

            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/DashboardAlumno.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);

            Stage stage = new Stage();
            stage.setTitle("Alumno");
            stage.setScene(scene);
            stage.show();

            // Cerrar la ventana de login
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } else {
            // Mostrar un mensaje de error si las credenciales son incorrectas
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de inicio de sesión");
            alert.setHeaderText(null);
            alert.setContentText("Usuario o contraseña incorrectos.");
            alert.showAndWait();
        }
    }
}