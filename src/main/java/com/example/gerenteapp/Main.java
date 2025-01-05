package com.example.gerenteapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/SaioHasiera.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 400, 300); // Ajusta el tamaño de la ventana según sea necesario
        scene.getStylesheets().add(getClass().getResource("/com/example/gerenteapp/css.css").toExternalForm());
        stage.setTitle("Saio Hasiera");
        stage.setMaximized(true);
        SaioHasieraController shc = fxmlLoader.getController();

        stage.setWidth(javafx.stage.Screen.getPrimary().getVisualBounds().getWidth());
        stage.setHeight(javafx.stage.Screen.getPrimary().getVisualBounds().getHeight());

        shc.setUsingStage(stage);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
