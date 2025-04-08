package com.example.gerenteapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ErabiltzaileaDB{

    public static ObservableList<Erabiltzailea> getErabiltzailea() {
        // Emaitzak gordetzeko zerrenda
        ObservableList<Erabiltzailea> erabiltzailea = FXCollections.observableArrayList();

        // Datuak lortzeko SQL kontsulta
        String query = "SELECT * FROM erabiltzailea"; // Aldatu "langileak" zure taularen izen errealera, desberdina bada

        try (Connection connection = ConnectionTest.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Emaitzak iteratu eta Langilea objektuak sortu
            while (resultSet.next()) {
                Erabiltzailea erabiltzaile = new Erabiltzailea(
                        resultSet.getInt("id"),
                        resultSet.getString("erabiltzaileIzena"),
                        resultSet.getString("pasahitza"),
                        resultSet.getInt("langilea_id"),
                        resultSet.getInt("langilea_mota"),
                        resultSet.getBoolean("txatBaimena")
                );
                erabiltzailea.add(erabiltzaile);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Errorea erakutsi zerbait gaizki badoa
        }

        return erabiltzailea;
    }


    public static void updateErabiltzailea(Erabiltzailea erabiltzailea) {
        String query = "UPDATE erronka1.erabiltzailea SET erabiltzaileIzena = ?, pasahitza = ?, txatBaimena = ? WHERE id = ?";
        try (Connection connection = ConnectionTest.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, erabiltzailea.getErabiltzaileIzena());
            stmt.setString(2, erabiltzailea.getPasahitza());
            stmt.setBoolean(3, erabiltzailea.getTxatBaimena());
            stmt.setInt(4, erabiltzailea.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteErabiltzailea(int id) {
        String query = "DELETE FROM erabiltzailea WHERE id = ?";
        try (Connection connection = ConnectionTest.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean validarErabiltzailea(String erabiltzaileIzena, String pasahitza) {
        String query = "SELECT id, langilea_mota, langilea_id FROM erabiltzailea WHERE erabiltzaileIzena = ? AND pasahitza = ?";

        try (Connection connection = ConnectionTest.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (connection == null) {
                System.out.println("No se pudo conectar a la base de datos.");
                return false;
            }

            preparedStatement.setString(1, erabiltzaileIzena);
            preparedStatement.setString(2, pasahitza);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int erabiltzaileaId = resultSet.getInt("id");
                    String langileaMota = resultSet.getString("langilea_mota");
                    int langileaId = resultSet.getInt("langilea_id");

                    // Imprimir los valores obtenidos para depuración
                    System.out.println("ID Usuario: " + erabiltzaileaId);
                    System.out.println("Tipo de usuario (langilea_mota): " + langileaMota);
                    System.out.println("ID Usuario: " + erabiltzaileaId);

                    // Verificar si langilea_mota es "4"
                    if ("4".equals(langileaMota)) {
                        return true;
                    } else {
                        System.out.println("El tipo de usuario no es 4");
                        return false;
                    }
                } else {
                    System.out.println("No se encontró el usuario con las credenciales proporcionadas.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error de base de datos: " + e.getMessage());
        }
        return false;
    }



    public boolean baimenaTxat(String erabiltzailea) {
        boolean baimena = false;
        String query = "SELECT COUNT(*) FROM erabiltzailea WHERE erabiltzaileIzena = ? AND txatBaimena = 1";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionTest.connect();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, erabiltzailea);

            rs = stmt.executeQuery();
            if (rs.next()) {
                baimena = rs.getInt(1) > 0; // COUNT(*) > 0 bada, true itzuli
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Arazoa gertatu da");
        } finally {
            // Itxi baliabideak
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return baimena;
    }
}
