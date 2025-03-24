package com.example.gerenteapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.io.IOException;

public class LehenOrriaController extends BaseController {

    @FXML
    private Button langileakButton;

    @FXML
    private Button LaguntzaButton;

    @FXML
    private Button erreserbakButton;

    @FXML
    private Button mahaiakButton;

    @FXML
    private Button chatIrekiButton;

    @FXML
    private Label errorLabel;

    /**
     * "Langileak" botoian klik egitean gertatzen dena.
     */
    @FXML
    private void onLangileakButtonClick() {
        aldatuEscena("/com/example/gerenteapp/LangileakKudeatu.fxml",
                "Langile Kudeaketa", langileakButton);
    }



    /**
     * "Erreserbak" botoian klik egitean gertatzen dena.
     */
    @FXML
    private void onErreserbakButtonClick() {
        aldatuEscena("/com/example/gerenteapp/ErreserbakKudeatu.fxml",
                "Erreserba Kudeaketa", erreserbakButton);
    }

    @FXML
    private void onChatIrekiButtonClick() {
        aldatuEscena("/com/example/gerenteapp/Chat.fxml",
                "Chat Ireki Kudeaketa", chatIrekiButton);
    }

    @FXML
    private void onMahaiakButtonClick() {
        aldatuEscena("/com/example/gerenteapp/MahaiakKudeatu.fxml",
                "Mahai kudeaketa", mahaiakButton);
    }


    /**
     * Eszena aldatu emandako datuekin.
     *
     * @param fxmlPath Eszenaren FXML fitxategiaren bidea.
     * @param izenburua Leiho berriaren izenburua.
     * @param button Botoia, uneko Stage-a eskuratzeko.
     */
    private void aldatuEscena(String fxmlPath, String izenburua, Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene eszenaBerria = new Scene(loader.load());

            eszenaBerria.getStylesheets().add(getClass().getResource("/com/example/gerenteapp/css.css").toExternalForm());

            Stage oraingoStagea = (Stage) button.getScene().getWindow();

            oraingoStagea.setScene(eszenaBerria);
            oraingoStagea.setTitle(izenburua);
            oraingoStagea.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            erakutsiErrorea("Orria ezin izan da kargatu :(");
        }
    }

    /**
     * Errore-mezua erakutsi etiketan.
     *
     * @param mezua Errore-mezua.
     */
    private void erakutsiErrorea(String mezua) {
        errorLabel.setText(mezua);
        errorLabel.setVisible(true);
    }

    /**
     * Erreserben kudeaketarako etorkizuneko funtzioak (beharrezkoa izanez gero).
     */
    @FXML
    public void handleErreserbakKudeatu() {
        // Behar izanez gero, hemen inplementatu.
    }

    public void onLaguntzaButtonClick(ActionEvent actionEvent) {
    }
}


