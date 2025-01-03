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

import java.io.IOException;
import java.time.LocalDate;

public class LangileakKudeatuController extends BaseController {

    @FXML
    private TableView<Langilea> langileTable;

    @FXML
    private TableColumn<Langilea, Integer> idColumn;

    @FXML
    private TableColumn<Langilea, String> dniColumn;

    @FXML
    private TableColumn<Langilea, String> izenaColumn;

    @FXML
    private TableColumn<Langilea, String> abizenaColumn;

    @FXML
    private TableColumn<Langilea, String> probintziaColumn;

    @FXML
    private TableColumn<Langilea, String> pkColumn;

    @FXML
    private TableColumn<Langilea, String> herriaColumn;

    @FXML
    private TableColumn<Langilea, String> helbideaColumn;

    @FXML
    private TableColumn<Langilea, String> emailaColumn;

    @FXML
    private TableColumn<Langilea, String> telfColumn;

    @FXML
    private TableColumn<Langilea, String> kkColumn;

    @FXML
    private TableColumn<Langilea, LocalDate> jaiotzeDataColumn;

    @FXML
    private TableColumn<Langilea, Void> accionColumn;

    @FXML
    private Button LangileakGehituButton;

    private ObservableList<Langilea> langileaList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configuración de las columnas con propiedades observables
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dniColumn.setCellValueFactory(new PropertyValueFactory<>("dni"));
        izenaColumn.setCellValueFactory(new PropertyValueFactory<>("izena"));
        abizenaColumn.setCellValueFactory(new PropertyValueFactory<>("abizena"));
        probintziaColumn.setCellValueFactory(new PropertyValueFactory<>("probintzia"));
        pkColumn.setCellValueFactory(new PropertyValueFactory<>("pk"));
        herriaColumn.setCellValueFactory(new PropertyValueFactory<>("herria"));
        helbideaColumn.setCellValueFactory(new PropertyValueFactory<>("helbidea"));
        emailaColumn.setCellValueFactory(new PropertyValueFactory<>("emaila"));
        telfColumn.setCellValueFactory(new PropertyValueFactory<>("telf"));
        kkColumn.setCellValueFactory(new PropertyValueFactory<>("kontuKorrontea"));
        jaiotzeDataColumn.setCellValueFactory(new PropertyValueFactory<>("jaiotzeData"));

        // Configurar columnas editables
        izenaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        abizenaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        telfColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Hacer la tabla editable
        langileTable.setEditable(true);

        // Detectar cambios en las columnas editables
        setupEditListeners();

        // Cargar datos desde LangileaDAO
        LangileaDAO langileaDAO = new LangileaDAO();
        langileaList = langileaDAO.getLangileak();
        langileTable.setItems(langileaList);

        // Agregar columna para acciones (eliminar y editar)
        addActionButtonsToTable();
    }

    private void setupEditListeners() {
        izenaColumn.setOnEditCommit(event -> {
            Langilea langilea = event.getRowValue();
            langilea.setIzena(event.getNewValue());
        });

        abizenaColumn.setOnEditCommit(event -> {
            Langilea langilea = event.getRowValue();
            langilea.setAbizena(event.getNewValue());
        });

        emailaColumn.setOnEditCommit(event -> {
            Langilea langilea = event.getRowValue();
            langilea.setEmaila(event.getNewValue());
        });

        telfColumn.setOnEditCommit(event -> {
            Langilea langilea = event.getRowValue();
            langilea.setTelf(event.getNewValue());
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
                    Langilea langilea = getTableView().getItems().get(getIndex());
                    confirmAndDelete(langilea);
                });

                editButton.setOnAction(event -> {
                    Langilea langilea = getTableView().getItems().get(getIndex());
                    saveEditedLangilea(langilea);
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

    public void updateLangileakTable() {
        // Obtener los datos actualizados desde la base de datos
        LangileaDAO langileaDAO = new LangileaDAO();
        ObservableList<Langilea> updatedLangileaList = langileaDAO.getLangileak();

        // Actualizar los datos del TableView
        langileaList.setAll(updatedLangileaList); // Reemplazar los elementos actuales
    }

    private void confirmAndDelete(Langilea langilea) {
        // Mostrar cuadro de diálogo de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de eliminación");
        alert.setHeaderText("¿Está seguro de que desea eliminar este registro?");
        alert.setContentText("Registro: " + langilea.getIzena() + " " + langilea.getAbizena());

        // Esperar la respuesta del usuario
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteLangilea(langilea);
            }
        });
    }

    private void saveEditedLangilea(Langilea langilea) {
        // Mostrar cuadro de diálogo de confirmación antes de guardar
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de guardado");
        alert.setHeaderText("¿Está seguro de que desea guardar los cambios?");
        alert.setContentText("Registro: " + langilea.getIzena() + " " + langilea.getAbizena());

        // Esperar la respuesta del usuario
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Guardar cambios en la base de datos
                LangileaDAO langileaDAO = new LangileaDAO();
                langileaDAO.updateLangilea(langilea);

                // Refrescar la tabla para mostrar los cambios
                langileTable.refresh();

                System.out.println("Registro actualizado: " + langilea);
            }
        });
    }

    private void deleteLangilea(Langilea langilea) {
        // Eliminar de la lista observable
        langileaList.remove(langilea);

        // Actualizar en la base de datos
        LangileaDAO langileaDAO = new LangileaDAO();
        langileaDAO.deleteLangilea(langilea.getId());

        System.out.println("Registro eliminado: " + langilea);
    }

    @FXML
    public void onLangileakGehituButtonClicked() {
        ShowLangileakGehitu();
    }

    @FXML
    public void onAtzeraButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/LehenOrria.fxml"));
            Scene escenaAnterior = new Scene(loader.load());
            Stage currentStage = (Stage) langileTable.getScene().getWindow();
            currentStage.setScene(escenaAnterior);
            currentStage.setTitle("Lehen Orria");
            currentStage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ShowLangileakGehitu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/LangileakGehitu.fxml"));
            Scene escenaLangileakGehitu = new Scene(loader.load());
            LangileakGehituController controller = loader.getController();
            controller.setParentController(this);
            Stage stage = new Stage();
            controller.setUsingStage(stage);
            stage.setScene(escenaLangileakGehitu);
            stage.setTitle("Langile Kudeaketa");
            stage.setWidth(670);
            stage.setHeight(460);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





