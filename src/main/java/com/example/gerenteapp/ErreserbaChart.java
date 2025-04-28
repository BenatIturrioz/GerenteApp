package com.example.gerenteapp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;


public class ErreserbaChart {

    private String bezeroKop;
    private int bezeroKopValue;

    public ErreserbaChart(String bezeroKop, Integer bezeroKopValue) {
        this.bezeroKop = bezeroKop;
        this.bezeroKopValue = bezeroKopValue;
    }

    public String getBezeroKop() {
        return bezeroKop;
    }

    public float getBezeroKopValue() {
        return bezeroKopValue;
    }

    public static List<ErreserbaChart> loadErreserbaList(){

        List<ErreserbaChart> chartData = new ArrayList<>();

        try(
                Connection connection = ConnectionTest.connect();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT bezeroKop, COUNT(id) as bezeroKopValue FROM erreserba GROUP BY bezeroKop")) {

            while (resultSet.next()) {
                String bezeroKop = resultSet.getString("bezeroKop");
                int bezeroKopValue = resultSet.getInt("bezeroKopValue");
                chartData.add(new ErreserbaChart(bezeroKop, bezeroKopValue));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return chartData;

    }
}
