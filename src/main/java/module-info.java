module com.example.gerenteapp {
    requires javafx.controls;
    requires javafx.fxml;


    requires org.controlsfx.controls;


    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.desktop;

    opens com.example.gerenteapp to javafx.fxml;
    exports com.example.gerenteapp;
}