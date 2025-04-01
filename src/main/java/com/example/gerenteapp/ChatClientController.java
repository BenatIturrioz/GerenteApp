package com.example.gerenteapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ChatClientController {

    @FXML
    private VBox messageArea; // Contenedor para mostrar los mensajes

    @FXML
    private TextField messageField; // Campo de entrada del mensaje

    @FXML
    private Button sendButton; // Botón para enviar el mensaje

    @FXML
    private Button atzeraButton;

    @FXML
    private Label lblUser;

    private PrintWriter out; // Para enviar mensajes al servidor
    private BufferedReader in; // Para recibir mensajes del servidor

    void setErabiltzailea(String izena){
        lblUser.setText(izena);
    }

    @FXML
    public void initialize() {
        connectToServer();

        // Acción del botón enviar
        sendButton.setOnAction(event -> handleSendMessage());

        // Acción al presionar Enter en el campo de texto
        messageField.setOnAction(event -> handleSendMessage());
    }

    private void handleSendMessage() {
        ErabiltzaileaDB lk = new ErabiltzaileaDB();
        boolean baimena = lk.baimenaTxat(lblUser.getText());
        String message = messageField.getText().trim(); // Capturar el mensaje
        if (!message.isEmpty()) {
            if (baimena) {
                addMessage(message, true); // Mostrar el mensaje enviado
                sendMessage(message); // Enviar al servidor
                messageField.clear(); // Limpiar el campo de texto
            }else{
                addMessage("Ez daukazu txatean idazteko baimenik", false);
            }
        }
    }

    private void connectToServer() {
        try {
            // Conexión al servidor
            Socket socket = new Socket("localhost", 5555);

            // Streams para enviar y recibir mensajes
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

            // Hilo para recibir mensajes
            Thread receiveThread = new Thread(() -> {
                try {
                    String incomingMessage;
                    while ((incomingMessage = in.readLine()) != null) {
                        String finalMessage = incomingMessage;
                        // Actualizar la interfaz con el mensaje recibido
                        Platform.runLater(() -> addMessage(finalMessage, false));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            receiveThread.setDaemon(true);
            receiveThread.start();
        } catch (IOException e) {
            Platform.runLater(() -> addMessage("Error al conectar con el servidor.", false));
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        if (out != null) {
            String prefixedMessage = "[Gerente] " + message; // Agregar prefijo al mensaje
            out.println(prefixedMessage); // Enviar mensaje con prefijo al servidor
        }
    }

    private void addMessage(String message, boolean isSentByUser) {
        // Crear el contenedor para el mensaje
        HBox messageBox = new HBox();
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);

        // Aplicar estilo según si el mensaje es enviado o recibido
        messageBox.setAlignment(Pos.CENTER_LEFT); // Alineación a la izquierda
        if (isSentByUser) {
            messageLabel.setStyle("-fx-background-color: #e1a067; -fx-text-fill: black; -fx-padding: 10; -fx-background-radius: 10;");
        } else {
            messageLabel.setStyle("-fx-background-color: #ff6600; -fx-text-fill: black; -fx-padding: 10; -fx-background-radius: 10;");
        }

        messageBox.getChildren().add(messageLabel);

        // Agregar el mensaje al área de mensajes
        Platform.runLater(() -> {
            messageArea.getChildren().add(messageBox);
            messageArea.layout(); // Asegurar que el layout se actualice
        });
    }

    @FXML
    private void onAtzeraButtonClicked() {
        atzeraItzuli("/com/example/gerenteapp/LehenOrria.fxml",
                "Saioa hasi", atzeraButton);
    }

    public void atzeraItzuli(String fxmlPath, String izenburua, Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene eszenaBerria = new Scene(loader.load());

            eszenaBerria.getStylesheets().add(getClass().getResource("/com/example/gerenteapp/css.css").toExternalForm());

            Stage oraingoStagea = (Stage) button.getScene().getWindow();

            oraingoStagea.setScene(eszenaBerria);
            oraingoStagea.setTitle(izenburua);
            oraingoStagea.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


