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
    private Label errorLabel;

    /**
     * Maneja el evento de clic en el botón "Langileak".
     */
    @FXML
    private void onLangileakButtonClick() {
        aldatuEscenaLangileakKudeatu();
    }

    /**
     * Maneja el evento de clic en el botón "Erreserbak".
     */
    @FXML
    private void onErreserbakButtonClick() {
        aldatuEscenaErreserbakKudeatu();
    }

    /**
     * Cambia a la escena de gestión de empleados.
     */
    private void aldatuEscenaLangileakKudeatu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/LangileakKudeatu.fxml"));
            Scene escenaLangileakKudeatu = new Scene(loader.load());

            // Obtener el Stage actual desde el botón
            Stage stageActual = (Stage) langileakButton.getScene().getWindow();

            stageActual.setScene(escenaLangileakKudeatu);
            stageActual.setTitle("Langile Kudeaketa");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Ezin da orria kargatu :(");
        }
    }

    /**
     * Cambia a la escena de gestión de reservas.
     */
    private void aldatuEscenaErreserbakKudeatu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/ErreserbakKudeatu.fxml"));
            Scene escenaErreserbakKudeatu = new Scene(loader.load());

            // Obtener el Stage actual desde el botón
            Stage stageActual = (Stage) erreserbakButton.getScene().getWindow();

            stageActual.setScene(escenaErreserbakKudeatu);
            stageActual.setTitle("Erreserba Kudeaketa");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Ezin da orria kargatu :(");
        }
    }

    /**
     * Muestra un mensaje de error en la etiqueta correspondiente.
     * @param mensaje Mensaje de error a mostrar.
     */
    private void mostrarError(String mensaje) {
        errorLabel.setText(mensaje);
        errorLabel.setVisible(true);
    }

    /**
     * Método vacío para futuros manejos de eventos de reservas (si aplica).
     */
    @FXML
    public void handleErreserbakKudeatu() {
        // Implementar si es necesario.
    }
}
