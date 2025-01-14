package com.example.gerenteapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

import java.io.*;
import java.net.Socket;

public class ChatClientController {

    @FXML
    private VBox messageArea;

    @FXML
    private TextField messageField;

    private PrintWriter out; // Para enviar mensajes al servidor

    private BufferedReader in; // Para recibir mensajes del servidor

    @FXML
    public void initialize() {
        connectToServer();
        messageField.setOnAction(event -> handleSendMessage());
    }

    @FXML
    private void handleSendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            addMessage(message, true); // Mostrar mensaje enviado
            sendMessage(message); // Enviar mensaje al servidor
            messageField.clear();
        }
    }

    private void connectToServer() {
        try {
            // Conectar al servidor en localhost y puerto 5555
            Socket socket = new Socket("localhost", 5555);

            // Crear streams de entrada y salida
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Hilo para recibir mensajes
            Thread receiveThread = new Thread(() -> {
                try {
                    String incomingMessage;
                    while ((incomingMessage = in.readLine()) != null) {
                        String finalMessage = incomingMessage;
                        // Actualizar la interfaz de usuario en el hilo de JavaFX
                        Platform.runLater(() -> addMessage(finalMessage, false)); // Mostrar mensaje recibido
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            receiveThread.setDaemon(true);
            receiveThread.start();
        } catch (IOException e) {
            Platform.runLater(() -> addMessage("No se pudo conectar al servidor.", false));
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        if (out != null) {
            out.println(message); // Enviar el mensaje al servidor
        }
    }

    private void addMessage(String message, boolean isSentByUser) {
        // Crear un contenedor para el mensaje
        HBox messageBox = new HBox();
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);

        // Estilizar el mensaje según sea enviado o recibido
        if (isSentByUser) {
            messageBox.setAlignment(Pos.CENTER_RIGHT);
            messageLabel.setStyle("-fx-background-color: lightblue; -fx-text-fill: black; -fx-padding: 10; -fx-background-radius: 10;");
        } else {
            messageBox.setAlignment(Pos.CENTER_LEFT);
            messageLabel.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-padding: 10; -fx-background-radius: 10;");
        }

        messageBox.getChildren().add(messageLabel);
        messageArea.getChildren().add(messageBox);
    }

}
