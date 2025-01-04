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
        String erabiltzaileIzena = usernameField.getText();
        String pasahitza = passwordField.getText();

        if (kredentzialakKudeatu(erabiltzaileIzena, pasahitza)) {
            errorLabel.setVisible(false);
            System.out.println("Saioa arrakastaz hasi da");
            aldatuLehenOrrira();
        } else {
            erakutsiErrorea("Erabiltzailea edo pasahitza ez da zuzena");
        }
    }

    /**
     * Erabiltzailearen kredentzialak egiaztatu.
     *
     * @param erabiltzaileIzena Erabiltzaile izena.
     * @param pasahitza         Pasahitza.
     * @return `true` kredentzialak zuzenak badira; bestela, `false`.
     */
    private boolean kredentzialakKudeatu(String erabiltzaileIzena, String pasahitza) {
        // Erabiltzailea bilatu datu-basean
        int id = Erabiltzailea.lortuIdKredentzialenArabera(erabiltzaileIzena, pasahitza);

        if (id > 0) {
            // Erabiltzailea aurkitu da, datuak kargatu behar izanez gero
            Erabiltzailea.bilatuErabiltzailea(id); // Aukerakoa: erabiltzailearen informazioa kargatu
            return true;
        } else {
            return false;
        }
    }

    /**
     * Lehen orrira aldatu saioa arrakastaz hasi ondoren.
     */
    private void aldatuLehenOrrira() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/LehenOrria.fxml"));
            Scene lehenOrria = new Scene(loader.load());

            // Oraingo leihoa eskuratu BaseController-etik
            Stage oraingoLeihoa = this.getUsingStage();

            oraingoLeihoa.setScene(lehenOrria);
            oraingoLeihoa.setTitle("Lehen Orria");
            oraingoLeihoa.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            erakutsiErrorea("Orria ezin izan da kargatu :(");
        }
    }

    /**
     * Errorea erakutsi erabiltzaileari.
     *
     * @param mezua Errorearen mezua.
     */
    private void erakutsiErrorea(String mezua) {
        errorLabel.setText(mezua);
        errorLabel.setVisible(true);
    }
}

