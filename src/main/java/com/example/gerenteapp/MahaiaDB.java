package com.example.gerenteapp;

import com.example.gerenteapp.ConnectionTest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MahaiaDB {

    /**
     * "Mahaia" taulako erregistro guztiak lortzeko metodoa.
     *
     * @return Mahaia objektuekin ObservableList bat.
     */
    public static ObservableList<Mahaia> getMahaia() {
        // Emaitzak gordetzeko zerrenda
        ObservableList<Mahaia> mahaiaList = FXCollections.observableArrayList();

        // Datuak lortzeko SQL kontsulta
        String query = "SELECT * FROM mahaia"; // Aldatu "mahaia" zure taularen benetako izenarekin, ezberdina bada

        try (Connection connection = ConnectionTest.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Emaitzetan iteratu eta Mahaia objektuak sortu
            while (resultSet.next()) {
                Mahaia mahaia = new Mahaia(
                        resultSet.getInt("mahaia_id"), // Aldatu "mahaia_id" zure zutabearen izen zehatzarekin
                        resultSet.getInt("zenbakia"),
                        resultSet.getInt("bezeroKopMax")
                );
                mahaiaList.add(mahaia);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Errorea erakutsi zerbait huts egiten badu
        }

        return mahaiaList;
    }

    /**
     * Datu-baseko mahaia baten datuak eguneratzeko metodoa.
     *
     * @param mahaia Datu berriekin Mahaia objektua.
     * @return true eguneratzea arrakastatsua izan bada, false kontrakoa bada.
     */
    public static boolean updateMahaia(Mahaia mahaia) {
        String query = "UPDATE mahaia SET zenbakia = ?, bezeroKopMax = ? WHERE mahaia_id = ?";

        try (Connection connection = ConnectionTest.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // PreparedStatement-aren parametroak ezarri
            statement.setInt(1, mahaia.getZenbakia());
            statement.setInt(2, mahaia.getBezeroKopMax());
            statement.setInt(3, mahaia.getMahaia_id());

            // Eguneratzea exekutatu
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Datu-baseko mahaia bat ezabatzeko metodoa.
     *
     * @param mahaia_id Mahaia objektuaren id-a.
     * @return true ezabaketa arrakastatsua izan bada, false kontrakoa bada.
     */
    public static boolean deleteMahaia(int mahaia_id) {
        String query = "DELETE FROM mahaia WHERE mahaia_id = ?";

        try (Connection connection = ConnectionTest.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Parametroa ezarri
            statement.setInt(1, mahaia_id);

            // Ezabaketa exekutatu
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

