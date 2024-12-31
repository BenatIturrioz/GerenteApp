package com.example.gerenteapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ErreserbakKudeatuController extends BaseController {

    public Button ErreserbaGehituButton;

    @FXML
    private TableView<Erreserba> erreserbaTable;

    @FXML
    private TableColumn<Erreserba, Integer> idColumn;

    @FXML
    private TableColumn<Erreserba, Integer> erreserbaIdColumn;

    @FXML
    private TableColumn<Erreserba, Integer> mahaiaIdColumn;

    @FXML
    private TableColumn<Erreserba, Integer> langileaIdColumn;

    @FXML
    private TableColumn<Erreserba, String> bezeroIzenaColumn;

    @FXML
    private TableColumn<Erreserba, Integer> telfColumn;

    @FXML
    private TableColumn<Erreserba, LocalDate> dataColumn;

    @FXML
    private TableColumn<Erreserba, Integer> bezroKopColumn;

    // Lista de datos para el TableView
    private ObservableList<Erreserba> erreserbaList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configuración de las columnas
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        erreserbaIdColumn.setCellValueFactory(new PropertyValueFactory<>("erreserba_id"));
        mahaiaIdColumn.setCellValueFactory(new PropertyValueFactory<>("mahaia_id"));
        langileaIdColumn.setCellValueFactory(new PropertyValueFactory<>("langilea_id"));
        bezeroIzenaColumn.setCellValueFactory(new PropertyValueFactory<>("bezeroIzena"));
        telfColumn.setCellValueFactory(new PropertyValueFactory<>("telf"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        bezroKopColumn.setCellValueFactory(new PropertyValueFactory<>("bezroKop"));

        // Usar ErreserbaDAO para obtener los datos de la base de datos
        ErreserbaDAO erreserbaDAO = new ErreserbaDAO();
        erreserbaList = erreserbaDAO.getErreserbak();

        // Asignar los datos obtenidos al TableView
        erreserbaTable.setItems(erreserbaList);
    }

    @FXML
    public void onErreserbaGehituButtonClicked() {
        ShowErreserbaGehitu();
    }

    private void addActionButtonsToTable() {
        accionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("🗑️");
            private final Button editButton = new Button("💾");

            {
                deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                editButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                deleteButton.setOnAction(event -> {
                    Erreserba erreserba = getTableView().getItems().get(getIndex());
                    confirmAndDelete(erreserba);
                });

                editButton.setOnAction(event -> {
                    Erreserba erreserba = getTableView().getItems().get(getIndex());
                    saveEditedLangilea(erreserba);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5, deleteButton, editButton);
                    setGraphic(buttons);
                }
            }
        });
    }

    private void confirmAndDelete(Erreserba erreserba) {
        // Mostrar cuadro de diálogo de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de eliminación");
        alert.setHeaderText("¿Está seguro de que desea eliminar este registro?");
        alert.setContentText("Registro: " + erreserba.getIzena() + " " + erreserba.getAbizena());

        // Esperar la respuesta del usuario
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteLangilea(erreserba);
            }
        });
    }

    @FXML
    public void onAtzeraButtonClicked() {
        try {
            // Cargar el archivo FXML de la ventana anterior
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/LehenOrria.fxml"));
            Scene escenaAnterior = new Scene(loader.load());

            // Obtener el Stage actual
            Stage currentStage = (Stage) erreserbaTable.getScene().getWindow();

            // Configurar la escena en el Stage actual
            currentStage.setScene(escenaAnterior);
            currentStage.setTitle("Lehen Orria");

            // Opcional: centrar la ventana
            currentStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ShowErreserbaGehitu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/ErreserbakGehitu.fxml"));
            Scene escenaErreserbaGehitu = new Scene(loader.load());

            // Crear un nuevo Stage para la nueva ventana
            Stage nuevoStage = new Stage();
            nuevoStage.setScene(escenaErreserbaGehitu);
            nuevoStage.setTitle("Erreserba Kudeaketa");

            // Configurar el tamaño deseado
            nuevoStage.setWidth(670);  // Establece el ancho deseado
            nuevoStage.setHeight(460); // Establece la altura deseada

            // Centrar la ventana en la pantalla
            nuevoStage.centerOnScreen();

            // Mostrar la nueva ventana
            nuevoStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




