package com.example.gerenteapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MahaiakGehituController extends BaseController {

    @FXML
    private TextField zenbakiaField;
    @FXML
    private TextField bezeroKopMaxField;
    @FXML
    private Button sortuButton;

    @FXML
    private void onSortuClick() {
        // Oinarrizko baliozkotzea
        if (zenbakiaField.getText().isEmpty() || bezeroKopMaxField.getText().isEmpty()) {
            showAlert("Errorea", "Zenbakia eta bezero kopuru maximoa ezin dira hutsik egon.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Sortu Mahaia berria
            int zenbakia = Integer.parseInt(zenbakiaField.getText());
            int bezeroKopMax = Integer.parseInt(bezeroKopMaxField.getText());

            Mahaia mahaia = new Mahaia(
                    0, // ID automatikoki sortua izan daiteke
                    zenbakia,
                    bezeroKopMax
            );

            // Gorde Mahaia datu-basean
            if (saveMahaia(mahaia)) {
                showAlert("Arrakasta", "Mahaia ondo sortu da.", Alert.AlertType.INFORMATION);

                // Eguneratu taula kontrolatzaile nagusian (indikatzen bada)
                if (parentController != null) {
                    parentController.updateMahaiaTable();
                }

                closeWindow();

            } else {
                showAlert("Errorea", "Errore bat gertatu da mahaia sortzerakoan.", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Errorea", "Errore bat gertatu da mahaia sortzerakoan.", Alert.AlertType.ERROR);
        }
    }

    private boolean saveMahaia(Mahaia mahaia) {
        String query = "INSERT INTO mahaia (zenbakia, bezeroKopMax) VALUES (?, ?)";

        try (Connection connection = ConnectionTest.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Esleitu balioak kontsultari
            stmt.setInt(1, mahaia.getZenbakia());
            stmt.setInt(2, mahaia.getBezeroKopMax());

            // Exekutatu kontsulta
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Lerroak gehitu badira, txertaketa arrakastatsua izan da

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
        // Itxi uneko leihoa oinarrizko klaseko Stage erabiliz (BaseController heredatua)
        if (usingStage != null) {
            usingStage.close();
        }
    }

    private MahaiaKudeatuController parentController;

    public void setParentController(MahaiaKudeatuController parentController) {
        this.parentController = parentController;
    }

    @FXML
    public void initialize() {
        // Crear un TextFormatter para solo aceptar números en el campo zenbakiaField con un máximo de 5 dígitos
        TextFormatter<String> zenbakiaFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            // Permitir solo dígitos y limitar a 5 caracteres
            if (newText.matches("\\d*") && newText.length() <= 5) {
                return change;
            }
            return null;  // Rechazar el cambio si no es un número o si supera los 5 caracteres
        });

        // Aplicar el TextFormatter al campo zenbakiaField
        zenbakiaField.setTextFormatter(zenbakiaFormatter);

        // Crear un TextFormatter para solo aceptar números en el campo bezeroKopMaxField con un máximo de 3 dígitos
        TextFormatter<String> bezeroKopMaxFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            // Permitir solo dígitos y limitar a 3 caracteres
            if (newText.matches("\\d*") && newText.length() <= 3) {
                return change;
            }
            return null;  // Rechazar el cambio si no es un número o si supera los 3 caracteres
        });

        // Aplicar el TextFormatter al campo bezeroKopMaxField
        bezeroKopMaxField.setTextFormatter(bezeroKopMaxFormatter);
    }
}


