package com.example.gerenteapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private void onEguraldiaButtonClick() {
        try {
            // Llama a la función que descarga el XML
            onHelloButtonClick();

            // Muestra alerta de confirmación
            showAlert("Éxito", "El tiempo se ha descargado correctamente");

        } catch (Exception e) {
            showAlert("Error", "No se pudo descargar el tiempo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    protected void onHelloButtonClick() throws ParserConfigurationException,
            NoSuchAlgorithmException, IOException, KeyManagementException, SAXException {

        UrlFindFinder.obtainDocument();
        UploadToFileZila.uploadFile();
    }

    public HelloController() {
        // Constructor vacío
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}