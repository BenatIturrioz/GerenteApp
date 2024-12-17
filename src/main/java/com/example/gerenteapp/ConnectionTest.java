package com.example.gerenteapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/erronka1";
        String user = "root";
        String password = "1WMG2023";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexión exitosa a la base de datos MySQL.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
