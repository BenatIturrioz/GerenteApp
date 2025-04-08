package com.example.gerenteapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {

    public static final String URL = "jdbc:mysql://localhost:3306/erronka1_test";
    public static final String USER = "root";
    public static final String PASS = "1WMG2023";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

