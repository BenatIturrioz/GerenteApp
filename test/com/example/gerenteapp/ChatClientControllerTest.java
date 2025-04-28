package com.example.gerenteapp;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.application.Platform;

class ChatClientControllerTest {
    @BeforeAll
    static void setupJavaFX() {
        Platform.startup(() -> {});
    }

    @Test
    void setErabiltzailea() {
        ChatClientController controller = new ChatClientController();
        controller.lblUser = new Label(); // Inicializar el label

        String usuarioPrueba = "Usuario123";
        controller.setErabiltzailea(usuarioPrueba);
        assertEquals(usuarioPrueba, controller.lblUser.getText());
    }

    @Test
    void initialize() {
        ChatClientController controller = new ChatClientController();
        controller.lblUser = new Label();
        controller.messageField = new TextField();
        controller.sendButton = new Button();
        controller.emojiButton = new Button();
        controller.fileButton = new Button();

        controller.initialize();
        assertNotNull(controller.sendButton.getOnAction());
        assertNotNull(controller.messageField.getOnAction());
        assertNotNull(controller.emojiButton.getOnAction());
        assertNotNull(controller.fileButton.getOnAction());
    }

    @Test
    void atzeraItzuli() {
        ChatClientController controller = new ChatClientController();
        controller.lblUser = new Label();
        controller.atzeraButton = new Button();

        // Crear un Stage y Scene en el hilo de JavaFX
        Platform.runLater(() -> {
            Stage stageMock = new Stage();
            Scene sceneMock = new Scene(new VBox());
            stageMock.setScene(sceneMock);  // Usar setScene en el Stage, no en el Button

            // Ejecutar el método a testear
            String fxmlPath = "/test.fxml";
            String titulo = "Prueba";
            controller.atzeraItzuli(fxmlPath, titulo, controller.atzeraButton);

            // Verificar que el título del stage se actualizó
            assertEquals(titulo, stageMock.getTitle());
        });
    }
}