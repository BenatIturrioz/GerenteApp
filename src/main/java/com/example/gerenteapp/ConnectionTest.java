package com.example.gerenteapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {

    public static final String URL = "jdbc:mysql://localhost:3306/erronka1";
    public static final String USER = "root";
    public static final String PASS = "1WMG2023";



    public static Connection connect() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
