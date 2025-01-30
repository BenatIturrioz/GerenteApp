package com.example.gerenteapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {

    public static final String URL = "jdbc:mysql://192.168.115.188:3306/erronka1";
    public static final String USER = "1taldea";
    public static final String PASS = "1taldea";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

