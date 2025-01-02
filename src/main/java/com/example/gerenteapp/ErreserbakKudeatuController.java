package com.example.gerenteapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.time.LocalDate;

public class ErreserbakKudeatuController extends BaseController {

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

    @FXML
    private TableColumn<Erreserba, Void> accionColumn;

    @FXML
    private Button ErreserbaGehituButton;

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

        // Configurar columnas editables
        bezeroIzenaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        telfColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        // Hacer la tabla editable
        erreserbaTable.setEditable(true);

        // Detectar cambios en las columnas editables
        setupEditListeners();

        // Cargar datos desde ErreserbaDAO
        ErreserbaDAO erreserbaDAO = new ErreserbaDAO();
        erreserbaList = erreserbaDAO.getErreserbak();
        erreserbaTable.setItems(erreserbaList);

        // Agregar columna para acciones (eliminar y editar)
        addActionButtonsToTable();
    }

    private void setupEditListeners() {
        bezeroIzenaColumn.setOnEditCommit(event -> {
            Erreserba erreserba = event.getRowValue();
            erreserba.setBezeroIzena(event.getNewValue());
        });

        telfColumn.setOnEditCommit(event -> {
            Erreserba erreserba = event.getRowValue();
            erreserba.setTelf(event.getNewValue());
        });
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
                    saveEditedErreserba(erreserba);
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

    public void updateErreserbakTable() {
        // Obtener los datos actualizados desde la base de datos
        ErreserbaDAO erreserbaDAO = new ErreserbaDAO();
        ObservableList<Erreserba> updatedErreserbaList = erreserbaDAO.getErreserbak();

        // Actualizar los datos del TableView
        erreserbaList.setAll(updatedErreserbaList);
    }

    private void confirmAndDelete(Erreserba erreserba) {
        // Mostrar cuadro de diálogo de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de eliminación");
        alert.setHeaderText("¿Está seguro de que desea eliminar este registro?");
        alert.setContentText("Registro: " + erreserba.getBezeroIzena());

        // Esperar la respuesta del usuario
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteErreserba(erreserba);
            }
        });
    }

    private void saveEditedErreserba(Erreserba erreserba) {
        // Mostrar cuadro de diálogo de confirmación antes de guardar
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de guardado");
        alert.setHeaderText("¿Está seguro de que desea guardar los cambios?");
        alert.setContentText("Registro: " + erreserba.getBezeroIzena());

        // Esperar la respuesta del usuario
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Guardar cambios en la base de datos
                ErreserbaDAO erreserbaDAO = new ErreserbaDAO();
                erreserbaDAO.updateErreserba(erreserba);

                // Refrescar la tabla para mostrar los cambios
                erreserbaTable.refresh();

                System.out.println("Registro actualizado: " + erreserba);
            }
        });
    }

    private void deleteErreserba(Erreserba erreserba) {
        // Eliminar de la lista observable
        erreserbaList.remove(erreserba);

        // Actualizar en la base de datos
        ErreserbaDAO erreserbaDAO = new ErreserbaDAO();
        erreserbaDAO.deleteErreserba(erreserba.getId());

        System.out.println("Registro eliminado: " + erreserba);
    }

    @FXML
    public void onErreserbaGehituButtonClicked() {
        ShowErreserbaGehitu();
    }

    @FXML
    public void onAtzeraButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/LehenOrria.fxml"));
            Scene escenaAnterior = new Scene(loader.load());
            Stage currentStage = (Stage) erreserbaTable.getScene().getWindow();
            currentStage.setScene(escenaAnterior);
            currentStage.setTitle("Lehen Orria");
            currentStage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ShowErreserbaGehitu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/ErreserbakGehitu.fxml"));
            Scene escenaErreserbaGehitu = new Scene(loader.load());
            Stage nuevoStage = new Stage();
            nuevoStage.setScene(escenaErreserbaGehitu);
            nuevoStage.setTitle("Erreserba Kudeaketa");
            nuevoStage.setWidth(670);
            nuevoStage.setHeight(460);
            nuevoStage.centerOnScreen();
            nuevoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





