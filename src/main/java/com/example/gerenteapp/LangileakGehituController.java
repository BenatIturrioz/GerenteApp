package com.example.gerenteapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    private ChoiceBox<String> motaChoiceBox;  // Hautaketa-koadroa lanpostu motarako
    @FXML
    private Label lblUser;

    void setErabiltzailea(String izena){
        lblUser.setText(izena);
    }
    @FXML
    private void onSortuClick() {
        // Oinarrizko baliozkotzea
        if (izenaField.getText().isEmpty() || abizenaField.getText().isEmpty() || dniField.getText().isEmpty()) {
            showAlert("Errorea", "Izena, abizena eta NAN ezin dira hutsik egon.", Alert.AlertType.ERROR);
            return;
        }

        // Egiaztatu lanpostu mota bat hautatu den
        String motaSeleccionada = motaChoiceBox.getValue();
        if (motaSeleccionada == null) {
            showAlert("Errorea", "Mesedez, hautatu mota.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Sortu Langile berria lanpostu motarekin
            Langilea langilea = new Langilea(
                    0, // ID automatikoki sortua izan daiteke
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
                    convertMotaToNumber(motaSeleccionada) // Hautatutako mota zenbakira bihurtu
            );

            // Gorde Langilea datu-basean
            if (saveLangilea(langilea)) {
                showAlert("Arrakasta", "Langilea ondo sortu da.", Alert.AlertType.INFORMATION);

                // Eguneratu taula kontrolatzaile nagusian
                if (parentController != null) {
                    parentController.updateLangileakTable();
                }

                closeWindow();

            } else {
                showAlert("Errorea", "Errore bat gertatu da langilea sortzerakoan.", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Errorea", "Errore bat gertatu da langilea sortzerakoan.", Alert.AlertType.ERROR);
        }
    }

    // Metodoa lanpostu mota zenbakira bihurtzeko
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

            // Esleitu balioak kontsultari
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
            stmt.setInt(12, langilea.getMota()); // Hemen mota gordetzen dugu

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

    private LangileakKudeatuController parentController;

    public void setParentController(LangileakKudeatuController parentController) {
        this.parentController = parentController;
    }

    @FXML
    public void initialize() {
        // Crear un TextFormatter para solo aceptar números en el campo de teléfono
        TextFormatter<String> telfFormatter = new TextFormatter<>(change -> {
            // Permitir solo dígitos
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;  // Rechazar el cambio si no es un número
        });

        // Aplicar el TextFormatter al campo de teléfono
        telfField.setTextFormatter(telfFormatter);

        // Crear un TextFormatter para solo aceptar números en el campo de PK
        TextFormatter<String> pkFormatter = new TextFormatter<>(change -> {
            // Permitir solo dígitos y limitar a 5 caracteres
            String newText = change.getControlNewText();
            if (newText.matches("\\d*") && newText.length() <= 5) {
                return change;
            }
            return null;  // Rechazar el cambio si no es un número o si supera los 5 caracteres
        });

        // Aplicar el TextFormatter al campo de PK
        pkField.setTextFormatter(pkFormatter);
    }

}




