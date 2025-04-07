package com.example.gerenteapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ErabiltzaileaGehituController extends BaseController{

    @FXML
    private TextField izenaField;
    @FXML
    private TextField erabiltzaileaField;
    @FXML
    private PasswordField pasahitzaField;
    @FXML
    private Button sortuButton;
    @FXML
    private Label lblUser;

    void setErabiltzailea(String izena) {
        lblUser.setText(izena);
    }

    private ErabiltzaileakKudeatuController parentController;
    // Establecer el controlador padre
    public void setParentController(ErabiltzaileakKudeatuController parentController) {
        this.parentController = parentController;
    }

    // Método ejecutado al hacer clic en "Sortu erreserba berria"
    @FXML
    private void onSortuClick(ActionEvent event) {
        // Recoger valores de los campos
        String langilea = izenaField.getText();
        String erabiltzailea = erabiltzaileaField.getText();
        String pasahitza = pasahitzaField.getText();


        // Validar campos
        if (langilea.isEmpty() || erabiltzailea.isEmpty() || pasahitza.isEmpty()) {
            mostrarError("Mesedez, bete eremu guztiak.");
            return;
        }


        // Intentar insertar los datos en la base de datos
        try {
            insertarReserva(langilea, erabiltzailea, pasahitza);


            // Actualizar tabla en el controlador padre
            if (parentController != null) {
                parentController.updateErabiltzaileaTable();
            }

            // Cerrar la ventana actual
            closeWindow();

        } catch (SQLException e) {
            mostrarError("Errorea erreserba sortzean: " + e.getMessage());
        }
    }

    private void insertarReserva(String izena, String erabiltzailea, String pasahitza) throws SQLException {
        // Conexión a la base de datos
        try (Connection connection = ConnectionTest.connect()) {
            if (connection == null) {
                mostrarError("Datu-basearekin konexio errorea.");
                return;
            }

            // Conseguir la id y el puesto del trabajador
            String query1 = "SELECT id, mota FROM langilea WHERE izena = ?";
            PreparedStatement stmt = connection.prepareStatement(query1);
            stmt.setString(1, izena);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                  int id = rs.getInt("id");
                  String mota = rs.getString("mota");
            try {
                // Consulta SQL para insertar la reserva
                String query = "INSERT INTO erabiltzailea (erabiltzaileIzena, pasahitza, langilea_id, langilea_mota, txatBaimena) "
                        + "VALUES (?, ?, ?, ?, 0)";

                // Preparar y ejecutar la consulta
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, erabiltzailea);
                    statement.setString(2, pasahitza);
                    statement.setInt(3, id);
                    statement.setString(4, mota);

                    statement.executeUpdate();
                }
            }catch (SQLException e) {
                mostrarError("Errorea erabiltzailea sortzean: " + e.getMessage());
            }
            }else{
                mostrarInfo("Ez da izen hori duen langilerik aurkitu");
            }

            rs.close();
            stmt.close();


        } catch (Exception e) {
           mostrarError("Errorea: " + e.getMessage());
        }
    }

    // Método para mostrar un error
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errorea");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para mostrar información
    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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