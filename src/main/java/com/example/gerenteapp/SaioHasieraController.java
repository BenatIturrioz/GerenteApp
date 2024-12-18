package com.example.gerenteapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaioHasieraController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private static final String URL = "jdbc:mysql://localhost:3306/erronka1";
    private static final String USER = "root";
    private static final String PASS = "1WMG2023";

    @FXML
    protected void onLoginButtonClick() {
        String erabiltzaileIzena = usernameField.getText();
        String pasahitza = passwordField.getText();

        if (erabiltzaileIzena.equals("admin") && pasahitza.equals("1234")) {
            errorLabel.setVisible(false);
            System.out.println("Inicio de sesión exitoso");
            // Aquí podrías cambiar de escena o continuar con el flujo de tu aplicación
        } else {
            errorLabel.setText("Usuario o contraseña incorrectos");
            errorLabel.setVisible(true);
        }
    }

    /**
     * Método para autenticar al usuario en la base de datos.
     *
     * @param erabiltzaileIzena Nombre de usuario ingresado.
     * @param pasahitza Contraseña ingresada.
     * @return `true` si las credenciales son válidas, `false` en caso contrario.
     */
    private boolean authenticateUser(String erabiltzaileIzena, String pasahitza) {
        String query = "SELECT * FROM erabiltzailea WHERE erabiltzaileIzena = ? AND pasahitza = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Configurar los parámetros de la consulta
            preparedStatement.setString(1, erabiltzaileIzena);
            preparedStatement.setString(2, pasahitza);

            // Ejecutar la consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            // Si la consulta devuelve resultados, las credenciales son válidas
            return resultSet.next();

        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Error al conectar con la base de datos.");
            errorLabel.setVisible(true);
            return false;
        }
    }
}
