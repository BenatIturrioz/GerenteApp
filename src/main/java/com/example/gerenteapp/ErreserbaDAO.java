package com.example.gerenteapp;

import com.example.gerenteapp.ConnectionTest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ErreserbaDAO {

    /**
     * Método para obtener todos los registros de la tabla "erreserbak".
     *
     * @return ObservableList con objetos Erreserba.
     */
    public ObservableList<Erreserba> getErreserbak() {
        // Lista para almacenar los resultados
        ObservableList<Erreserba> erreserbak = FXCollections.observableArrayList();

        // Consulta SQL para obtener los datos
        String query = "SELECT * FROM erreserba"; // Cambia "erreserba" por el nombre real de tu tabla, si es diferente

        try (Connection connection = ConnectionTest.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Iterar sobre los resultados y crear objetos Erreserba
            while (resultSet.next()) {
                Erreserba erreserba = new Erreserba(
                        resultSet.getInt("id"), // Cambia "id" por el nombre exacto de tu columna
                        resultSet.getInt("erreserba_id"),
                        resultSet.getInt("mahaia_id"),
                        resultSet.getInt("langilea_id"),
                        resultSet.getString("bezeroIzena"),
                        resultSet.getInt("telf"),
                        resultSet.getDate("data") != null ? resultSet.getDate("data").toLocalDate() : LocalDate.ofEpochDay(Integer.parseInt(null)),

                        resultSet.getInt("bezeroKop")
                );
                erreserbak.add(erreserba);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Muestra el error si algo falla
        }

        return erreserbak;
    }

    /**
     * Método para actualizar los datos de una reserva en la base de datos.
     *
     * @param erreserba El objeto Erreserba con los nuevos datos.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean updateErreserba(Erreserba erreserba) {
        String query = "UPDATE erreserba SET erreserba_id = ?, mahaia_id = ?, langilea_id = ?, bezeroIzena = ?, telf = ?, " +
                "data = ?, bezeroKop = ? WHERE id = ?";

        try (Connection connection = ConnectionTest.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Establecer los parámetros del PreparedStatement
            statement.setInt(1, erreserba.getErreserba_id());
            statement.setInt(2, erreserba.getMahaia_id());
            statement.setInt(3, erreserba.getLangilea_id());
            statement.setString(4, erreserba.getBezeroIzena());
            statement.setInt(5, erreserba.getTelf());
            statement.setDate(6, java.sql.Date.valueOf(erreserba.getData())); // Convertir LocalDate a java.sql.Date
            statement.setInt(7, erreserba.getBezroKop());
            statement.setInt(8, erreserba.getId());

            // Ejecutar la actualización
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método para eliminar una reserva de la base de datos.
     *
     * @param id El ID de la reserva a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean deleteErreserba(int id) {
        String query = "DELETE FROM erreserba WHERE id = ?";

        try (Connection connection = ConnectionTest.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Establecer el parámetro
            statement.setInt(1, id);

            // Ejecutar la eliminación
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}







