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

public class LangileaDB {

    public static ObservableList<Langilea> getLangileak() {
        // Emaitzak gordetzeko zerrenda
        ObservableList<Langilea> langileak = FXCollections.observableArrayList();

        // Datuak lortzeko SQL kontsulta
        String query = "SELECT * FROM erronka1.langilea;"; // Aldatu "langileak" zure taularen izen errealera, desberdina bada

        try (Connection connection = ConnectionTest.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Emaitzak iteratu eta Langilea objektuak sortu
            while (resultSet.next()) {
                Langilea langilea = new Langilea(
                        resultSet.getInt("id"),
                        resultSet.getString("dni"),
                        resultSet.getString("izena"),
                        resultSet.getString("abizena"),
                        resultSet.getString("probintzia"),
                        resultSet.getString("pk"),
                        resultSet.getString("herria"),
                        resultSet.getString("helbidea"),
                        resultSet.getString("emaila"),
                        resultSet.getString("telf"),
                        resultSet.getString("kontuKorrontea"),
                        resultSet.getDate("jaiotzeData").toLocalDate(),
                        resultSet.getInt("mota")
                );
                langileak.add(langilea);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Errorea erakutsi zerbait gaizki badoa
        }

        return langileak;
    }

    public static void updateLangilea(Langilea langilea) {
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

    public static void deleteLangilea(int id) {
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





