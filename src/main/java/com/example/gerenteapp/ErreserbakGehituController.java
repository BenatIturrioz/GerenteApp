package com.example.gerenteapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ErreserbakGehituController extends BaseController {

    // FXML eremuak
    @FXML private TextField bezeroIzenaField;
    @FXML private TextField telfField;
    @FXML private DatePicker dataPicker;
    @FXML private TextField bezroKopField;
    @FXML private TextField mahaiaIdField;
    @FXML private TextField langileaIdField;
    @FXML private TextField erreserbaIdField;

    // "Sortu erreserba berria" botoia klik egitean exekutatuko den metodoa
    @FXML
    private void onSortuClick(ActionEvent event) {
        // Eremuetako balioak bildu
        String bezeroIzena = bezeroIzenaField.getText();
        String telefonoa = telfField.getText();
        String data = dataPicker.getValue() != null ? dataPicker.getValue().toString() : "";
        String bezeroKopurua = bezroKopField.getText();
        String mahaiaId = mahaiaIdField.getText();
        String langileaId = langileaIdField.getText();
        String erreserbaId = erreserbaIdField.getText();

        // Egiaztatu eremu guztiak beteta dauden
        if (bezeroIzena.isEmpty() || telefonoa.isEmpty() || data.isEmpty() || bezeroKopurua.isEmpty() || mahaiaId.isEmpty() || langileaId.isEmpty() || erreserbaId.isEmpty()) {
            mostrarError("Mesedez, bete eremu guztiak.");
            return;
        }

        // Datuak datu-basean sartzen saiatu
        try {
            insertarReserva(bezeroIzena, telefonoa, data, bezeroKopurua, mahaiaId, langileaId, erreserbaId);
            mostrarInfo("Erreserba arrakastaz sortu da!");

            // Taula eguneratu kontroladore nagusian
            if (parentController != null) {
                parentController.updateErreserbakTable();
            }

            closeWindow();

        } catch (SQLException e) {
            mostrarError("Errorea erreserba sortzean: " + e.getMessage());
        }
    }

    // Erreserba datu-basean sartzeko metodoa
    private void insertarReserva(String bezeroIzena, String telefonoa, String data, String bezeroKopurua, String mahaiaId, String langileaId, String erreserbaId) throws SQLException {
        // Konexioa lortu ConnectionTest-etik
        try (Connection connection = ConnectionTest.connect()) {
            if (connection == null) {
                mostrarError("Datu-basearekin konexio errorea.");
                return;
            }

            // Erreserba sartzeko SQL kontsulta
            String query = "INSERT INTO erreserba (erreserba_id, bezeroIzena, telf, data, bezeroKop, mahaia_id, langilea_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            // PreparedStatement sortu
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // PreparedStatement-aren parametroak ezarri
                statement.setString(1, erreserbaId);
                statement.setString(2, bezeroIzena);
                statement.setString(3, telefonoa);
                statement.setString(4, data);
                statement.setInt(5, Integer.parseInt(bezeroKopurua));
                statement.setInt(6, Integer.parseInt(mahaiaId));
                statement.setInt(7, Integer.parseInt(langileaId));

                // Kontsulta exekutatu
                statement.executeUpdate();
            }
        }
    }

    // Errorea erakusteko metodoa
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Errorea");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Informazio mezu bat erakusteko metodoa
    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informazioa");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private ErreserbakKudeatuController parentController;

    public void setParentController(ErreserbakKudeatuController parentController) {
        this.parentController = parentController;
    }

    private void closeWindow() {
        // Uneko leihoa itxi BaseController-etik herentzian dagoen Stage erabiliz
        if (usingStage != null) {
            usingStage.close();
        }
    }
}








