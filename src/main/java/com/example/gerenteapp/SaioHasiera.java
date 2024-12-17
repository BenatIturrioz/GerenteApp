package com.example.gerenteapp;

import javafx.fxml.FXML;
import javafx.scene.control.Labeled;
import javafx.scene.control.*;

public class SaioHasiera {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    protected void onLoginButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("admin") && password.equals("1234")) {
            errorLabel.setVisible(false);
            System.out.println("Inicio de sesión exitoso");
            // Aquí podrías cambiar de escena o continuar con el flujo de tu aplicación
        } else {
            errorLabel.setText("Usuario o contraseña incorrectos");
            errorLabel.setVisible(true);
        }
    }
}
