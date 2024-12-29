package com.example.gerenteapp;

import com.example.gerenteapp.ConnectionTest;
import com.example.gerenteapp.Langilea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class LangileaDAO {

    /**
     * Método para obtener todos los registros de la tabla "langileak".
     *
     * @return ObservableList con objetos Langilea.
     */
    public ObservableList<Langilea> getLangileak() {
        // Lista para almacenar los resultados
        ObservableList<Langilea> langileak = FXCollections.observableArrayList();

        // Consulta SQL para obtener los datos
        String query = "SELECT * FROM langilea"; // Cambia "langileak" por el nombre real de tu tabla, si es diferente

        try (Connection connection = ConnectionTest.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Iterar sobre los resultados y crear objetos Langilea
            while (resultSet.next()) {
                Langilea langilea = new Langilea(
                        resultSet.getInt("id"), // Cambia "id" por el nombre exacto de tu columna
                        resultSet.getString("dni"), // Cambia "dni" por el nombre exacto de tu columna
                        resultSet.getString("izena"),
                        resultSet.getString("abizena"),
                        resultSet.getString("probintzia"),
                        resultSet.getString("pk"),
                        resultSet.getString("herria"),
                        resultSet.getString("helbidea"),
                        resultSet.getString("emaila"),
                        resultSet.getString("telf"),
                        resultSet.getString("kontuKorrontea"),
                        resultSet.getDate("jaiotzeData").toLocalDate(), // Convertimos java.sql.Date a LocalDate
                        resultSet.getInt("mota")
                );
                langileak.add(langilea);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Muestra el error si algo falla
        }

        return langileak;
    }

    public void updateLangilea(Langilea langilea) {
        String query = "UPDATE langilea SET izena = ?, abizena = ?, emaila = ?, telf = ? WHERE id = ?";
        try (Connection connection = ConnectionTest.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, langilea.getIzena());
            stmt.setString(2, langilea.getAbizena());
            stmt.setString(3, langilea.getEmaila());
            stmt.setString(4, langilea.getTelf());
            stmt.setInt(5, langilea.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLangilea(int id) {
        String query = "DELETE FROM langilea WHERE id = ?";
        try (Connection connection = ConnectionTest.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




