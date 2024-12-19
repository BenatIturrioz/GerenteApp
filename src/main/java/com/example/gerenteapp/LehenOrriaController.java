package com.example.gerenteapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class LehenOrriaController extends BaseController{

    @FXML
    private Button langileakButton;

    @FXML
    private Button erreserbakButton;

    @FXML
    private Label errorLabel;

    private void aldatuEscenaLangileakKudeatu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/LangileakKudeatu.fxml"));
            Scene escenaLehenOrria = new Scene(loader.load());

            Stage stageActual = this.getUsingStage();

            stageActual.setScene(escenaLehenOrria);
            stageActual.setTitle("Langile Kudeaketa");

        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Ezin da orria kargatu :(");
            errorLabel.setVisible(true);
        }
    }


    @FXML
    public void handleErreserbakKudeatu(){

    }
}
