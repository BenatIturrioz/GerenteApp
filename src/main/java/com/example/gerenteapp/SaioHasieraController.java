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

    @FXML
    private Label lblUser;

    private String setErabiltzailea(String erabiltzaileIzena){
        return erabiltzaileIzena;
    }
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
            // Verificar las credenciales
            if (ErabiltzaileaDB.validarErabiltzailea(erabiltzaileIzena, pasahitza)) {
                erakutsiErrorea("Saioa hasita.");
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
            Stage oraingoLeihoa = (Stage) usernameField.getScene().getWindow();

            String erabiltzaileIzena = usernameField.getText().trim();
            LehenOrriaController controller = loader.getController();
            controller.setErabiltzailea(erabiltzaileIzena);

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


