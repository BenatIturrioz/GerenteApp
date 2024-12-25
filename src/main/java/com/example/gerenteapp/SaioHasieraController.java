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

public class SaioHasieraController extends BaseController{

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;



    @FXML
    protected void onLoginButtonClick() {
        String erabiltzaileIzena = usernameField.getText();
        String pasahitza = passwordField.getText();

        if (kredentzialakKudeatu(erabiltzaileIzena, pasahitza)) {
            errorLabel.setVisible(false);
            System.out.println("Inicio de sesión exitoso");
            aldatuEscenaLehenOrria();
        } else {
            errorLabel.setText("Usuario o contraseña incorrectos");
            errorLabel.setVisible(true);
        }
    }

    /**
     * Método para autenticar al usuario en la base de datos.
     *
     * @param erabiltzaileIzena Erabiltzaile izena.
     * @param pasahitza Pasahitza.
     * @return `true`
     */
    private boolean kredentzialakKudeatu(String erabiltzaileIzena, String pasahitza) {
        int id = Erabiltzailea.lortuIdKredentzialenArabera(erabiltzaileIzena, pasahitza);

        if (id > 0) {
            // Usuario encontrado, buscar datos completos (si es necesario)
            Erabiltzailea erabiltzailea = Erabiltzailea.bilatuErabiltzailea(id);
            return true;
        } else {
            // Usuario no encontrado o credenciales incorrectas
            return false;
        }
    }


    private void aldatuEscenaLehenOrria() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/LehenOrria.fxml"));
            Scene escenaLehenOrria = new Scene(loader.load());

            Stage stageActual = this.getUsingStage();

            stageActual.setScene(escenaLehenOrria);
            stageActual.setTitle("Lehen Orria");

        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Ezin da orria kargatu :(");
            errorLabel.setVisible(true);
        }
    }
}
