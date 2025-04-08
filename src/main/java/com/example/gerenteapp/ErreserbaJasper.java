package com.example.gerenteapp;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class ErreserbaJasper {
    private String bezeroIzena;
    private int mahaia_id;
    private int bezeroKop;

    public ErreserbaJasper(String bezeroIzena, int mahaia_id, int bezeroKop) {
        this.bezeroIzena = bezeroIzena;
        this.mahaia_id = mahaia_id;
        this.bezeroKop = bezeroKop;
    }

    public String getBezeroIzena() {
        return bezeroIzena;
    }

    public int getMahaia_id() {
        return mahaia_id;
    }

    public int getBezeroKop() {
        return bezeroKop;
    }

    public static List<ErreserbaJasper> loadErreserbaList(){
        List<ErreserbaJasper> erreserbak = new ArrayList<ErreserbaJasper>();

        try(
                Connection connection = ConnectionTest.connect();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT bezeroIzena, mahaia_id, bezeroKop FROM erreserba")){

            while(resultSet.next()){
                String bezeroIzena = resultSet.getString("bezeroIzena");
                int mahaia_id = resultSet.getInt("mahaia_id");
                int bezeroKop = resultSet.getInt("bezeroKop");

                erreserbak.add(new ErreserbaJasper(bezeroIzena, mahaia_id, bezeroKop));

            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return erreserbak;
    }
}
