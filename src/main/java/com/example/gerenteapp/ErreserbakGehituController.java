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

    // FXML Fields
    @FXML private TextField bezeroIzenaField;
    @FXML private TextField telfField;
    @FXML private DatePicker dataPicker;
    @FXML private TextField bezroKopField;
    @FXML private TextField mahaiaIdField;
    @FXML private TextField langileaIdField;
    @FXML private TextField erreserbaIdField;

    // Método que se ejecutará cuando se haga clic en el botón "Sortu erreserba berria"
    @FXML
    private void onSortuClick(ActionEvent event) {
        // Recoger los valores de los campos
        String bezeroIzena = bezeroIzenaField.getText();
        String telefonoa = telfField.getText();
        String data = dataPicker.getValue() != null ? dataPicker.getValue().toString() : "";
        String bezeroKopurua = bezroKopField.getText();
        String mahaiaId = mahaiaIdField.getText();
        String langileaId = langileaIdField.getText();
        String erreserbaId = erreserbaIdField.getText();

        // Verificar si todos los campos están completos
        if (bezeroIzena.isEmpty() || telefonoa.isEmpty() || data.isEmpty() || bezeroKopurua.isEmpty() || mahaiaId.isEmpty() || langileaId.isEmpty() || erreserbaId.isEmpty()) {
            mostrarError("Por favor, completa todos los campos.");
            return;
        }

        // Intentar insertar los datos en la base de datos
        try {
            insertarReserva(bezeroIzena, telefonoa, data, bezeroKopurua, mahaiaId, langileaId, erreserbaId);
            mostrarInfo("¡Reserva creada con éxito!");

            // Actualizar la tabla en el controlador principal
            if (parentController != null) {
                parentController.updateErreserbakTable();
            }

            closeWindow();

        } catch (SQLException e) {
            mostrarError("Error al crear la reserva: " + e.getMessage());
        }
    }

    // Método para insertar la reserva en la base de datos
    private void insertarReserva(String bezeroIzena, String telefonoa, String data, String bezeroKopurua, String mahaiaId, String langileaId, String erreserbaId) throws SQLException {
        // Obtener la conexión desde ConnectionTest
        try (Connection connection = ConnectionTest.connect()) {
            if (connection == null) {
                mostrarError("Error de conexión a la base de datos.");
                return;
            }

            // Query SQL para insertar la reserva
            String query = "INSERT INTO erreserba (erreserba_id, bezeroIzena, telf, data, bezeroKop, mahaia_id, langilea_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Crear el PreparedStatement
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Establecer los parámetros del PreparedStatement
                statement.setString(1, erreserbaId);
                statement.setString(2, bezeroIzena);
                statement.setString(3, telefonoa);
                statement.setString(4, data);
                statement.setInt(5, Integer.parseInt(bezeroKopurua));
                statement.setInt(6, Integer.parseInt(mahaiaId));
                statement.setInt(7, Integer.parseInt(langileaId));

                // Ejecutar la consulta
                statement.executeUpdate();
            }
        }
    }

    // Método para mostrar un mensaje de error
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para mostrar un mensaje informativo
    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private ErreserbakKudeatuController parentController;

    public void setParentController(ErreserbakKudeatuController parentController) {
        this.parentController = parentController;
    }

    private void closeWindow() {
        // Cierra la ventana actual utilizando el Stage de la clase base (heredado de BaseController)
        if (usingStage != null) {
            usingStage.close();
        }
    }
}







