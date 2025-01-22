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
    private ErreserbakKudeatuController parentController;
    // Establecer el controlador padre
    public void setParentController(ErreserbakKudeatuController parentController) {
        this.parentController = parentController;
    }

    // Método ejecutado al hacer clic en "Sortu erreserba berria"
    @FXML
    private void onSortuClick(ActionEvent event) {
        // Recoger valores de los campos
        String bezeroIzena = bezeroIzenaField.getText();
        String telefonoa = telfField.getText();
        String data = dataPicker.getValue() != null ? dataPicker.getValue().toString() : "";
        String bezeroKopurua = bezroKopField.getText();
        String mahaiaId = mahaiaIdField.getText();


        // Validar campos
        if (bezeroIzena.isEmpty() || telefonoa.isEmpty() || data.isEmpty() || bezeroKopurua.isEmpty() || mahaiaId.isEmpty()) {
            mostrarError("Mesedez, bete eremu guztiak.");
            return;
        }

        // Validar que los campos numéricos son válidos
        try {
            Integer.parseInt(bezeroKopurua);
            Integer.parseInt(mahaiaId);
        } catch (NumberFormatException e) {
            mostrarError("Bezero kopurua eta mahaia ID zenbaki baliozkoak izan behar dira.");
            return;
        }

        // Intentar insertar los datos en la base de datos
        try {
            insertarReserva(bezeroIzena, telefonoa, data, bezeroKopurua, mahaiaId);


            // Actualizar tabla en el controlador padre
            if (parentController != null) {
                parentController.updateErreserbakTable();
            }

            // Cerrar la ventana actual
            closeWindow();

        } catch (SQLException e) {
            mostrarError("Errorea erreserba sortzean: " + e.getMessage());
        }
    }

    private void insertarReserva(String bezeroIzena, String telefonoa, String data, String bezeroKopurua, String mahaiaId) throws SQLException {
        // Conexión a la base de datos
        try (Connection connection = ConnectionTest.connect()) {
            if (connection == null) {
                mostrarError("Datu-basearekin konexio errorea.");
                return;
            }

            // Verificar si ya existe una reserva para la misma mesa y fecha
            String checkQuery = "SELECT COUNT(*) FROM erreserba WHERE data = ? AND mahaia_id = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, data);
                checkStatement.setInt(2, Integer.parseInt(mahaiaId));
                try (var resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        mostrarError("Errorea: Mahaia hori data horretan dagoeneko erreserbatuta dago.");
                        return;
                    }else {
                        mostrarInfo("Erreserba arrakastaz sortu da!");
                    }
                }
            }

            // Consulta SQL para insertar la reserva
            String query = "INSERT INTO erreserba (bezeroIzena, telf, data, bezeroKop, mahaia_id) "
                    + "VALUES (?, ?, ?, ?, ?)";

            // Preparar y ejecutar la consulta
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, bezeroIzena);
                statement.setString(2, telefonoa);
                statement.setString(3, data);
                statement.setInt(4, Integer.parseInt(bezeroKopurua));
                statement.setInt(5, Integer.parseInt(mahaiaId));

                statement.executeUpdate();
            }

        }
    }

    // Método para mostrar un error
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Errorea");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para mostrar información
    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informazioa");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para cerrar la ventana
    private void closeWindow() {
        // Cierra la ventana usando la referencia a `Stage` en la clase `BaseController`
        if (usingStage != null) {
            usingStage.close();
        }
    }
}









