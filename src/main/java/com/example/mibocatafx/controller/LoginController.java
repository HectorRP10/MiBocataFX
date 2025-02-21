package com.example.mibocatafx.controller;

import com.example.mibocatafx.MainApplication;
import com.example.mibocatafx.UsuarioSesion;
import com.example.mibocatafx.dao.UsuarioDao;
import com.example.mibocatafx.models.Usuario;
import com.example.mibocatafx.service.BocadilloService;
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

            FXMLLoader fxmlLoader = null;
            String title = "";

            if (usuario.getTipo() == Usuario.Tipo.Alumno) {

                fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/nav_alumno.fxml"));
                title = "Alumno";
            } else if (usuario.getTipo() == Usuario.Tipo.Cocina) {
                fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/mibocatafx/fxml/PedidosCocina.fxml"));
                title = "Cocina";

            } else if (usuario.getTipo() == Usuario.Tipo.Admin) {
                fxmlLoader  = new FXMLLoader(MainApplication.class.getResource("/com/example/mibocatafx/fxml/Administrador.fxml"));
                title = "Admin";

            }

            if (fxmlLoader != null) {
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                Stage stage = new Stage();
                stage.setTitle(title);
                stage.setScene(scene);
                stage.setMaximized(true);  // Maximizar la ventana
                stage.show();

                //Si es alumno cargo el center al iniciar
                if (usuario.getTipo() == Usuario.Tipo.Alumno) {
                    DashboardAlumnoController controller = fxmlLoader.getController();
                    controller.cargarCenter();
                }

                // Cerrar la ventana de login
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
            }

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