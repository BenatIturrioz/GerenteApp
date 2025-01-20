package com.example.gerenteapp;

import java.sql.*;

public class Erabiltzailea {
    private String erabiltzaileIzena;
    private String pasahitza;
    private int erabiltzaileaId;
    private String langileaMota;

    // Getters y Setters
    public String getErabiltzaileIzena() {
        return erabiltzaileIzena;
    }

    public void setErabiltzaileIzena(String erabiltzaileIzena) {
        this.erabiltzaileIzena = erabiltzaileIzena;
    }

    public String getPasahitza() {
        return pasahitza;
    }

    public void setPasahitza(String pasahitza) {
        this.pasahitza = pasahitza;
    }

    public int getErabiltzaileaId() {
        return erabiltzaileaId;
    }

    public void setErabiltzaileaId(int erabiltzaileaId) {
        this.erabiltzaileaId = erabiltzaileaId;
    }

    public String getLangileaMota() {
        return langileaMota;
    }

    public void setLangileaMota(String langileaMota) {
        this.langileaMota = langileaMota;
    }

    // Método para validar las credenciales
    public boolean validarErabiltzailea() {
        String query = "SELECT id, langilea_mota FROM erabiltzailea WHERE erabiltzaileIzena = ? AND pasahitza = ?";

        // Conectarse a la base de datos usando la clase ConnectionTest
        try (Connection connection = ConnectionTest.connect();  // Utiliza el método connect de ConnectionTest
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (connection == null) {
                System.out.println("No se pudo conectar a la base de datos.");
                return false;
            }

            preparedStatement.setString(1, this.erabiltzaileIzena);
            preparedStatement.setString(2, this.pasahitza);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    this.erabiltzaileaId = resultSet.getInt("id");
                    this.langileaMota = resultSet.getString("langilea_mota");

                    // Imprimir los valores obtenidos para depuración
                    System.out.println("ID Usuario: " + this.erabiltzaileaId);
                    System.out.println("Tipo de usuario (langilea_mota): " + this.langileaMota);

                    // Verificar si langilea_mota es "3"
                    if ("4".equals(this.langileaMota)) {
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
}


