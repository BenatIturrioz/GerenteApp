package com.example.gerenteapp;

import com.example.gerenteapp.ConnectionTest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ErreserbaDB {

    /**
     * "Erreserbak" taulako erregistro guztiak lortzeko metodoa.
     *
     * @return Erreserba objektuekin ObservableList bat.
     */
    public static ObservableList<Erreserba> getErreserbak() {
        // Emaitzak gordetzeko zerrenda
        ObservableList<Erreserba> erreserbak = FXCollections.observableArrayList();

        // Datuak lortzeko SQL kontsulta
        String query = "SELECT * FROM erreserba"; // Aldatu "erreserba" zure taularen benetako izenarekin, ezberdina bada

        try (Connection connection = ConnectionTest.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Emaitzetan iteratu eta Erreserba objektuak sortu
            while (resultSet.next()) {
                Erreserba erreserba = new Erreserba(
                        resultSet.getInt("id"), // Aldatu "id" zure zutabearen izen zehatzarekin
                        resultSet.getInt("erreserba_id"),
                        resultSet.getString("mahaia_id"),
                        resultSet.getInt("langilea_id"),
                        resultSet.getString("bezeroIzena"),
                        resultSet.getString("telf"),
                        resultSet.getDate("data") != null ? resultSet.getDate("data").toLocalDate() : LocalDate.ofEpochDay(Integer.parseInt(null)),
                        resultSet.getString("bezeroKop")
                );
                erreserbak.add(erreserba);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Errorea erakutsi zerbait huts egiten badu
        }

        return erreserbak;
    }

    /**
     * Datu-baseko erreserba baten datuak eguneratzeko metodoa.
     *
     * @param erreserba Datu berriekin Erreserba objektua.
     * @return true eguneratzea arrakastatsua izan bada, false kontrakoa bada.
     */
    public static boolean updateErreserba(Erreserba erreserba) {
        String query = "UPDATE erreserba SET erreserba_id = ?, mahaia_id = ?, langilea_id = ?, bezeroIzena = ?, telf = ?, " +
                "data = ?, bezeroKop = ? WHERE id = ?";

        try (Connection connection = ConnectionTest.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // PreparedStatement-aren parametroak ezarri
            statement.setInt(1, erreserba.getErreserba_id());
            statement.setString(2, erreserba.getMahaia_id());
            statement.setInt(3, erreserba.getLangilea_id());
            statement.setString(4, erreserba.getBezeroIzena());
            statement.setString(5, erreserba.getTelf());
            statement.setDate(6, java.sql.Date.valueOf(erreserba.getData())); // LocalDate java.sql.Date bihurtu
            statement.setString(7, erreserba.getBezroKop());
            statement.setInt(8, erreserba.getId());

            // Eguneratzea exekutatu
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean deleteErreserba(int id) {
        String query = "DELETE FROM erreserba WHERE id = ?";

        try (Connection connection = ConnectionTest.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Parametroa ezarri
            statement.setInt(1, id);

            // Ezabaketa exekutatu
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}








