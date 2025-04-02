package com.example.gerenteapp;

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
    private Button erreserbakButton;

    @FXML
    private Button mahaiakButton;

    @FXML
    private Button chatIrekiButton;

    @FXML
    private Button erabiltzaileaKudeatuButton;

    @FXML
    private Button saioaItxiButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Label lblUser;

    void setErabiltzailea(String izena){
        lblUser.setText(izena);
    }

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

    @FXML
    private void onSaioaItxiButtonClick() {
        aldatuEscena("/com/example/gerenteapp/SaioHasiera.fxml",
                "Saioa hasi", saioaItxiButton);
    }

    @FXML
    private void onErabiltzaileaKudeatuButtonClick() {
        aldatuEscena("/com/example/gerenteapp/ErabiltzaileakKudeatu.fxml",
                "Erabiltzaile kudeaketa", erabiltzaileaKudeatuButton);
    }

    /**
     * Eszena aldatu emandako datuekin.
     *
     * @param fxmlPath Eszenaren FXML fitxategiaren bidea.
     * @param izenburua Leiho berriaren izenburua.
     * @param button Botoia, uneko Stage-a eskuratzeko.
     */
    private void aldatuEscena(String fxmlPath, String izenburua, Button button) {
        ErabiltzaileaDB lk = new ErabiltzaileaDB();
        boolean baimena = lk.baimenaTxat(lblUser.getText());
        String erabiltzaileIzena = lblUser.getText().trim();
        try {
            if (izenburua.equals("Chat Ireki Kudeaketa")) {
                if (baimena) {
                    FXMLLoader load = new FXMLLoader(getClass().getResource(fxmlPath));
                    Scene eszenaBerria = new Scene(load.load());

                    eszenaBerria.getStylesheets().add(getClass().getResource("/com/example/gerenteapp/css.css").toExternalForm());

                    Stage oraingoStagea = (Stage) button.getScene().getWindow();

                    oraingoStagea.setScene(eszenaBerria);
                    oraingoStagea.setTitle(izenburua);
                    oraingoStagea.centerOnScreen();

                    ChatClientController controller = load.getController();
                    controller.setErabiltzailea(erabiltzaileIzena);
                } else {
                    erakutsiErrorea("Ez daukazu baimenik txatean sartzeko");
                }
            }else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Scene eszenaBerria = new Scene(loader.load());

                eszenaBerria.getStylesheets().add(getClass().getResource("/com/example/gerenteapp/css.css").toExternalForm());

                Stage oraingoStagea = (Stage) button.getScene().getWindow();

                oraingoStagea.setScene(eszenaBerria);
                oraingoStagea.setTitle(izenburua);
                oraingoStagea.centerOnScreen();
                if(izenburua.equals("Langile Kudeaketa")) {
                    LangileakKudeatuController controller = loader.getController();
                    controller.setErabiltzailea(erabiltzaileIzena);
                }else if(izenburua.equals("Erreserba Kudeaketa")) {
                    ErreserbakKudeatuController controller = loader.getController();
                    controller.setErabiltzailea(erabiltzaileIzena);
                }else if(izenburua.equals("Mahai kudeaketa")) {
                    MahaiaKudeatuController controller = loader.getController();
                    controller.setErabiltzailea(erabiltzaileIzena);
                }else if(izenburua.equals("Erabiltzaile kudeaketa")) {
                    ErabiltzaileakKudeatuController controller = loader.getController();
                    controller.setErabiltzailea(erabiltzaileIzena);
                }

            }
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
}


