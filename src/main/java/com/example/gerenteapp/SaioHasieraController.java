package com.example.gerenteapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class SaioHasieraController extends BaseController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    /**
     * Saio-hasierako botoian klik egitean gertatzen dena.
     */
    @FXML
    protected void onLoginButtonClick() {
        String erabiltzaileIzena = usernameField.getText().trim();
        String pasahitza = passwordField.getText().trim();

        if (erabiltzaileIzena.isEmpty() || pasahitza.isEmpty()) {
            erakutsiErrorea("Sartzeko erabiltzailea eta pasahitza sartu behar da");
            return;
        }

        try {
            Erabiltzailea erabiltzailea = new Erabiltzailea();
            erabiltzailea.setErabiltzaileIzena(erabiltzaileIzena);
            erabiltzailea.setPasahitza(pasahitza);

            // Verificar las credenciales
            if (erabiltzailea.validarErabiltzailea()) {

                erakutsiErrorea("Saioa hasita.");

                // Lógica adicional si es necesario
                int erabiltzaileaId = erabiltzailea.getErabiltzaileaId();
                String langileaMota = erabiltzailea.getLangileaMota();
                int langileaId = erabiltzailea.getLangileaId();

                System.out.println("ErabiltzaileaId: " + erabiltzaileaId);
                System.out.println("LangileaMota: " + langileaMota);

                // Aquí puedes cambiar a una nueva pantalla, por ejemplo, LehenOrria.fxml
                aldatuLehenOrrira();
            } else {
                erakutsiErrorea("Ez duzu baimenik sartzeko.");
            }
        } catch (Exception e) {
            erakutsiErrorea("Saioa hasterakoan arazoa: " + e.getMessage());
        }
    }

    private void aldatuLehenOrrira() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/LehenOrria.fxml"));
            Scene lehenOrria = new Scene(loader.load());

            lehenOrria.getStylesheets().add(getClass().getResource("/com/example/gerenteapp/css.css").toExternalForm());
            Stage oraingoLeihoa = this.getUsingStage();

            oraingoLeihoa.setScene(lehenOrria);
            oraingoLeihoa.setTitle("Lehen Orria");
            oraingoLeihoa.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            erakutsiErrorea("Orria ezin izan da kargatu :(");
        }
    }

    private void erakutsiErrorea(String mezua) {
        errorLabel.setText(mezua);
        errorLabel.setVisible(true);
    }
}


