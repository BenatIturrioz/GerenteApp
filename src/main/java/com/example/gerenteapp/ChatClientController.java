package com.example.gerenteapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class ChatClientController {

    @FXML
    VBox messageArea;

    @FXML
    TextField messageField;

    @FXML
    Button sendButton;
    @FXML
    Button atzeraButton;
    @FXML
    Button emojiButton;
    @FXML
    Button fileButton;

    @FXML
    Label lblUser;

    private PrintWriter out;
    private BufferedReader in;
    private DataOutputStream dataOut;

    void setErabiltzailea(String izena) {
        lblUser.setText(izena);
    }

    @FXML
    public void initialize() {
        connectToServer();
        sendButton.setOnAction(event -> handleSendMessage());
        messageField.setOnAction(event -> handleSendMessage());
        emojiButton.setOnAction(event -> showEmojiMenu());
        fileButton.setOnAction(event -> handleSendFile());
    }

    private void handleSendMessage() {
        ErabiltzaileaDB lk = new ErabiltzaileaDB();
        boolean baimena = lk.baimenaTxat(lblUser.getText());
        String mensaje = messageField.getText().trim();
        String message = "["+lblUser.getText()+"] "+mensaje;

        // Validación de mensaje vacío
        if (message.isEmpty()) {
            return; // No hacer nada si está vacío
        }

        if (baimena) {
            try {
                String encryptedMessage = EncryptionUtils.encrypt(message);
                // Solo proceder si el cifrado fue exitoso
                if (encryptedMessage != null && !encryptedMessage.isEmpty()) {
                    addMessage(message, true);
                    sendMessage(encryptedMessage);
                    messageField.clear();
                } else {
                    addMessage("Arazoa mezua zifratzerakoan", false);
                }
            } catch (Exception e) {
                addMessage("Arazoa mezua zifratzerakoan", false);
            }
        } else {
            addMessage("Ez daukazu txatean idazteko baimenik", false);
        }
    }

    private void connectToServer() {
        try {
            //Socket socket = new Socket("localhost", 5555);
            Socket socket = new Socket("192.168.115.158", 5555);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            dataOut = new DataOutputStream(socket.getOutputStream());

            Thread receiveThread = new Thread(() -> {
                try {
                    String incomingMessage;
                    while ((incomingMessage = in.readLine()) != null) {
                        String decryptedMessage = EncryptionUtils.decrypt(incomingMessage);
                        if (decryptedMessage != null && decryptedMessage.startsWith("[FITXATEGIA]")) {
                            receiveFileFromServer(decryptedMessage, socket);
                        } else {
                            if (decryptedMessage != null && !decryptedMessage.isEmpty()) {
                                Platform.runLater(() -> addMessage(decryptedMessage, false));
                            }
                        }
                    }
                } catch (IOException e) {
                    Platform.runLater(() -> addMessage("Errorea zerbitzaritik irakurtzean.", false));
                    e.printStackTrace();
                }
            });
            receiveThread.setDaemon(true);
            receiveThread.start();
        } catch (IOException e) {
            Platform.runLater(() -> addMessage("Errorea zerbitzarira konektatzerakoan.", false));
            e.printStackTrace();
        }
    }

    private void handleSendFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Aukeratu bidali nahi duzun fitxategia");
        File file = fileChooser.showOpenDialog(fileButton.getScene().getWindow());

        if (file != null) {
            try {
                byte[] fileBytes = Files.readAllBytes(file.toPath());

                // Primero enviar metadatos
                String metadata = "[FITXATEGIA]" + file.getName() + ":" + fileBytes.length;
                String encryptedMetadata = EncryptionUtils.encrypt(metadata);
                sendMessage(encryptedMetadata);

                // Luego enviar el archivo
                dataOut.write(fileBytes);
                dataOut.flush();

                // Crear botón de descarga para el archivo
                addFileDownloadButton(file.getName(), fileBytes);
            } catch (IOException e) {
                addMessage("Arazoa fitxategia bidaltzerakoan", false);
                e.printStackTrace();
            }
        }
    }

    private void receiveFileFromServer(String decryptedMetadata, Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            String[] parts = decryptedMetadata.substring(11).split(":", 2); // Quitar "[FITXATEGIA]"

            if (parts.length != 2) {
                Platform.runLater(() -> addMessage("Fitxategiaren metadatuak okerrak dira", false));
                return;
            }

            String fileName = parts[0];
            int fileSize;
            try {
                fileSize = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                Platform.runLater(() -> addMessage("Fitxategiaren tamaina baliogabea", false));
                return;
            }

            byte[] fileBytes = new byte[fileSize];
            int totalRead = 0;
            while (totalRead < fileSize) {
                int read = inputStream.read(fileBytes, totalRead, fileSize - totalRead);
                if (read == -1) {
                    throw new IOException("Ezinezkoa da fitxategi osoa jasotzea");
                }
                totalRead += read;
            }

            // Mostrar botón de descarga
            Platform.runLater(() -> {
                addFileDownloadButton(fileName, fileBytes);
            });

        } catch (Exception e) {
            Platform.runLater(() -> addMessage("Errorea fitxategia jasotzean: " + e.getMessage(), false));
            e.printStackTrace();
        }
    }

    private void addFileDownloadButton(String fileName, byte[] fileBytes) {
        String izena = lblUser.getText();
        HBox fileBox = new HBox();
        Button downloadButton = new Button("["+ izena + "] "+ fileName);
        downloadButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 5; -fx-background-radius: 5;");
        downloadButton.setOnAction(event -> downloadFile(fileName, fileBytes));
        fileBox.setAlignment(Pos.CENTER_LEFT);
        fileBox.getChildren().add(downloadButton);
        Platform.runLater(() -> messageArea.getChildren().add(fileBox));
    }

    private void downloadFile(String fileName, byte[] fileBytes) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Gorde fitxategia");
        fileChooser.setInitialFileName(fileName);
        File file = fileChooser.showSaveDialog(messageArea.getScene().getWindow());

        if (file != null) {
            try {
                Files.write(file.toPath(), fileBytes);
                addMessage("Fitxategia gordeta: " + file.getName(), true);
            } catch (IOException e) {
                addMessage("Arazoa fitxategia gordetzerakoan", false);
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    private void addMessage(String message, boolean isSentByUser) {
        HBox messageBox = new HBox();
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);

        messageBox.setAlignment(Pos.CENTER_LEFT);
        if (isSentByUser) {
            messageLabel.setStyle("-fx-background-color: #e1a067; -fx-text-fill: black; -fx-padding: 10; -fx-background-radius: 10;");
        } else {
            messageLabel.setStyle("-fx-background-color: #ff6600; -fx-text-fill: black; -fx-padding: 10; -fx-background-radius: 10;");
        }

        messageBox.getChildren().add(messageLabel);
        Platform.runLater(() -> messageArea.getChildren().add(messageBox));
    }

    private void showEmojiMenu() {
        ContextMenu emojiMenu = new ContextMenu();
        String[] emojis = {"😍", "😎", "👍", "🎉", "😃", "😄", "😁", "😆", "😅", "🤣", "😂", "🙂", "🙃", "😉", "😊", "😘", "😴", "🤧", "😨", "😰", "😭", "😱", "😈", "👿", "💀", "🙈", "🙉", "🙊", "❤️"};

        for (String emoji : emojis) {
            MenuItem item = new MenuItem(emoji);
            item.setOnAction(e -> messageField.appendText(emoji));
            emojiMenu.getItems().add(item);
        }

        emojiMenu.show(emojiButton, javafx.geometry.Side.BOTTOM, 0, 0);
    }

    @FXML
    private void onAtzeraButtonClicked() {
        atzeraItzuli("/com/example/gerenteapp/LehenOrria.fxml", "Saioa hasi", atzeraButton);
    }

    public void atzeraItzuli(String fxmlPath, String izenburua, Button button) {
        String erabiltzaileIzena = lblUser.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene eszenaBerria = new Scene(loader.load());
            eszenaBerria.getStylesheets().add(getClass().getResource("/com/example/gerenteapp/css.css").toExternalForm());
            Stage oraingoStagea = (Stage) button.getScene().getWindow();
            oraingoStagea.setScene(eszenaBerria);
            oraingoStagea.setTitle(izenburua);
            oraingoStagea.centerOnScreen();

            LehenOrriaController controller = loader.getController();
            controller.setErabiltzailea(erabiltzaileIzena);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}












