package com.example.gerenteapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Erabiltzailea extends ConnectionTest {

    private int id;
    private String erabiltzaile_izena;
    private String pasahitza;
    private int id_langilea;



    public Erabiltzailea(int id, String erabiltzaile_izena, String pasahitza, int id_langilea) {
        this.id = id;
        this.erabiltzaile_izena = erabiltzaile_izena;
        this.pasahitza = pasahitza;
        this.id_langilea = id_langilea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getErabiltzaile_izena() {
        return erabiltzaile_izena;
    }

    public void setErabiltzaile_izena(String erabiltzaile_izena) {
        this.erabiltzaile_izena = erabiltzaile_izena;
    }

    public String getPasahitza() {
        return pasahitza;
    }

    public void setPasahitza(String pasahitza) {
        this.pasahitza = pasahitza;
    }

    public int getId_langilea() {
        return id_langilea;
    }

    public void setId_langilea(int id_langilea) {
        this.id_langilea = id_langilea;
    }

    public static int lortuIdKredentzialenArabera(String erabiltzaileIzena, String pasahitza){

        String query = "SELECT id FROM erabiltzailea WHERE erabiltzaileIzena = ? AND pasahitza = ?";


        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, erabiltzaileIzena);
            preparedStatement.setString(2, pasahitza);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");

                System.out.println("Inicio de sesión exitoso");
                return id;
            }
            else
            {
                return 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }


    }

    public static Erabiltzailea bilatuErabiltzailea(int id){

        String query = "SELECT * FROM erabiltzailea WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int erabiltzaileaId = resultSet.getInt("id");
                String erabiltzaileIzena = resultSet.getString("erabiltzaileIzena");
                String pasahitza = resultSet.getString("pasahitza");
                int id_langilea = resultSet.getInt("langilea_id");

                System.out.println("Inicio de sesión exitoso");
                Erabiltzailea e = new Erabiltzailea(erabiltzaileaId,erabiltzaileIzena,pasahitza,id_langilea);
                return e;
            }
            else
            {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
