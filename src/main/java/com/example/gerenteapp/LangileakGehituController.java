package com.example.gerenteapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class LangileakGehituController extends BaseController {

    @FXML
    private TextField izenaField;
    @FXML
    private TextField abizenaField;
    @FXML
    private TextField dniField;
    @FXML
    private TextField probintziaField;
    @FXML
    private TextField pkField;
    @FXML
    private TextField herriaField;
    @FXML
    private TextField helbideaField;
    @FXML
    private TextField emailaField;
    @FXML
    private TextField telfField;
    @FXML
    private TextField kontuKorronteaField;
    @FXML
    private DatePicker jaiotzeDataPicker;
    @FXML
    private Button sortuButton;
    @FXML
    private ChoiceBox<String> motaChoiceBox;  // ChoiceBox para el tipo de puesto

    @FXML
    private void onSortuClick() {
        // Validación básica
        if (izenaField.getText().isEmpty() || abizenaField.getText().isEmpty() || dniField.getText().isEmpty()) {
            showAlert("Error", "Izena, abizena eta DNI ezin dira hutsik egon.", Alert.AlertType.ERROR);
            return;
        }

        // Verificar si se ha seleccionado un tipo de puesto
        String motaSeleccionada = motaChoiceBox.getValue();
        if (motaSeleccionada == null) {
            showAlert("Error", "Mesedez, hautatu mota.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Crear nuevo Langilea con el tipo de puesto
            Langilea langilea = new Langilea(
                    0, // ID puede ser generado automáticamente
                    dniField.getText(),
                    izenaField.getText(),
                    abizenaField.getText(),
                    probintziaField.getText(),
                    pkField.getText(),
                    herriaField.getText(),
                    helbideaField.getText(),
                    emailaField.getText(),
                    telfField.getText(),
                    kontuKorronteaField.getText(),
                    jaiotzeDataPicker.getValue(),
                    convertMotaToNumber(motaSeleccionada) // Convertir la mota seleccionada a número
            );

            // Guardar el Langilea en la base de datos
            if (saveLangilea(langilea)) {
                showAlert("Arrakasta", "Langilea ondo sortu da.", Alert.AlertType.INFORMATION);

                // Actualizar la tabla en el controlador principal
                if (parentController != null) {
                    parentController.updateLangileakTable();
                }

                closeWindow();

            } else {
                showAlert("Error", "Errore bat gertatu da langilea sortzerakoan.", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Errore bat gertatu da langilea sortzerakoan.", Alert.AlertType.ERROR);
        }
    }

    // Método para convertir el tipo de puesto a un número
    private int convertMotaToNumber(String mota) {
        switch (mota) {
            case "Zerbitzaria":
                return 1;
            case "Sukaldaria":
                return 2;
            case "Administratzailea":
                return 3;
            case "Gerentea":
                return 4;
            default:
                return 0;
        }
    }

    private boolean saveLangilea(Langilea langilea) {
        String query = "INSERT INTO langilea (dni, izena, abizena, probintzia, pk, herria, helbidea, emaila, telf, kontuKorrontea, jaiotzeData, mota) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionTest.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Asignar los valores a la consulta
            stmt.setString(1, langilea.getDni());
            stmt.setString(2, langilea.getIzena());
            stmt.setString(3, langilea.getAbizena());
            stmt.setString(4, langilea.getProbintzia());
            stmt.setString(5, langilea.getPk());
            stmt.setString(6, langilea.getHerria());
            stmt.setString(7, langilea.getHelbidea());
            stmt.setString(8, langilea.getEmaila());
            stmt.setString(9, langilea.getTelf());
            stmt.setString(10, langilea.getKontuKorrontea());
            stmt.setObject(11, langilea.getJaiotzeData());
            stmt.setInt(12, langilea.getMota()); // Aquí guardamos la mota

            // Ejecutar la consulta
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Si se insertaron filas, la inserción fue exitosa

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void closeWindow() {
        // Cierra la ventana actual utilizando el Stage de la clase base (heredado de BaseController)
        if (usingStage != null) {
            usingStage.close();
        }
    }

    private LangileakKudeatuController parentController;

    public void setParentController(LangileakKudeatuController parentController) {
        this.parentController = parentController;
    }



}



