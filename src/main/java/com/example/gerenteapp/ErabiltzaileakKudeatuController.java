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
import javafx.util.converter.BooleanStringConverter;

import java.io.IOException;

public class ErabiltzaileakKudeatuController {

    @FXML
    private TableView<Erabiltzailea> erabiltzaileTable;

    @FXML
    private TableColumn<Erabiltzailea, Integer> idColumn;
    @FXML
    private TableColumn<Erabiltzailea, String> erabiltzaileaIzenaColumn;
    @FXML
    private TableColumn<Erabiltzailea, String> pasahitzaColumn;
    @FXML
    private TableColumn<Erabiltzailea, Integer> langileMotaColumn;
    @FXML
    private TableColumn<Erabiltzailea, Integer> langileIdColumn;
    @FXML
    private TableColumn<Erabiltzailea, Boolean> txatBaimenaColumn;
    @FXML
    private TableColumn<Erabiltzailea, Void> accionColumn;

    @FXML
    private Label lblUser;

    void setErabiltzailea(String izena){
        lblUser.setText(izena);
    }

    @FXML
    private Button ErabiltzaileaGehituButton;

    private ObservableList<Erabiltzailea> erabiltzaileList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configureColumns();
        setupEditListeners();
        loadErabiltzaileakData();
        addActionButtonsToTable();
    }

    private void configureColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        erabiltzaileaIzenaColumn.setCellValueFactory(new PropertyValueFactory<>("erabiltzaileIzena"));
        pasahitzaColumn.setCellValueFactory(new PropertyValueFactory<>("pasahitza"));
        langileIdColumn.setCellValueFactory(new PropertyValueFactory<>("langilea_id"));
        langileMotaColumn.setCellValueFactory(new PropertyValueFactory<>("langilea_mota"));
        txatBaimenaColumn.setCellValueFactory(new PropertyValueFactory<>("txatBaimena"));

        txatBaimenaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));

        // Make all columns editable
        erabiltzaileaIzenaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        pasahitzaColumn.setCellFactory(TextFieldTableCell.forTableColumn());;

        erabiltzaileTable.setEditable(true);
    }

    private void setupEditListeners() {
        idColumn.setOnEditCommit(event -> event.getRowValue().setId(event.getNewValue()));
        erabiltzaileaIzenaColumn.setOnEditCommit(event -> event.getRowValue().setErabiltzaileIzena(event.getNewValue()));
        pasahitzaColumn.setOnEditCommit(event -> event.getRowValue().setPasahitza(event.getNewValue()));
        langileIdColumn.setOnEditCommit(event -> event.getRowValue().setLangilea_id(event.getNewValue()));
        langileMotaColumn.setOnEditCommit(event -> event.getRowValue().setLangilea_mota(event.getNewValue()));
        txatBaimenaColumn.setOnEditCommit(event -> event.getRowValue().setTxatBaimena(event.getNewValue()));
    }

    private void loadErabiltzaileakData() {
        erabiltzaileList = ErabiltzaileaDB.getErabiltzailea();
        erabiltzaileTable.setItems(erabiltzaileList);
    }

    private void addActionButtonsToTable() {
        accionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("🗑️");
            private final Button editButton = new Button("💾");

            {
                setupButtonActions();
            }

            private void setupButtonActions() {
                deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                editButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                deleteButton.setOnAction(event -> confirmAndDelete(getTableView().getItems().get(getIndex())));
                editButton.setOnAction(event -> saveEditedErabiltzailea(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : new HBox(5, deleteButton, editButton));
            }
        });
    }

    public void updateErabiltzaileaTable() {
        erabiltzaileList.setAll(ErabiltzaileaDB.getErabiltzailea());
    }

    private void confirmAndDelete(Erabiltzailea erabiltzailea) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Está seguro de eliminar este registro?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) deleteErabiltzailea(erabiltzailea);
        });
    }

    private void saveEditedErabiltzailea(Erabiltzailea erabiltzailea) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Desea guardar los cambios?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                ErabiltzaileaDB.updateErabiltzailea(erabiltzailea);
                erabiltzaileTable.refresh();
            }
        });
    }

    @FXML
    private void ShowErabiltzaileakGehitu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/ErabiltzaileakGehitu.fxml"));
            Scene scene = new Scene(loader.load());
            ErabiltzaileaGehituController controller = loader.getController();
            controller.setParentController(this);

            Stage stage = new Stage();
            controller.setUsingStage(stage);
            stage.setScene(scene);
            stage.setTitle("Erabiltzaile Kudeaketa");
            stage.setWidth(670);
            stage.setHeight(460);
            stage.centerOnScreen();
            stage.show();

            String erabiltzaileIzena = lblUser.getText().trim();
            controller.setErabiltzailea(erabiltzaileIzena);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteErabiltzailea(Erabiltzailea erabiltzailea) {
        erabiltzaileList.remove(erabiltzailea);
        ErabiltzaileaDB.deleteErabiltzailea(erabiltzailea.getId());
    }

    @FXML
    public void onAtzeraButtonClicked() {
        String erabiltzaileIzena = lblUser.getText().trim();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/LehenOrria.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/com/example/gerenteapp/css.css").toExternalForm());
            Stage stage = (Stage) erabiltzaileTable.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Lehen Orria");
            stage.centerOnScreen();
            LehenOrriaController controller = loader.getController();
            controller.setErabiltzailea(erabiltzaileIzena);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
