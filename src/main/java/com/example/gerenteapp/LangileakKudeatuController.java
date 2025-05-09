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
    private Label lblUser;

    void setErabiltzailea(String izena){
        lblUser.setText(izena);
    }
    @FXML
    private Button LangileakGehituButton;

    private ObservableList<Langilea> langileaList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configureColumns();
        setupEditListeners();
        loadLangileakData();
        addActionButtonsToTable();
    }

    private void configureColumns() {
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

        // Make all columns editable
        izenaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        abizenaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        telfColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dniColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        probintziaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        pkColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        herriaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        helbideaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        kkColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        langileTable.setEditable(true);
    }

    private void setupEditListeners() {
        izenaColumn.setOnEditCommit(event -> event.getRowValue().setIzena(event.getNewValue()));
        abizenaColumn.setOnEditCommit(event -> event.getRowValue().setAbizena(event.getNewValue()));
        emailaColumn.setOnEditCommit(event -> event.getRowValue().setEmaila(event.getNewValue()));
        telfColumn.setOnEditCommit(event -> event.getRowValue().setTelf(event.getNewValue()));
        dniColumn.setOnEditCommit(event -> event.getRowValue().setDni(event.getNewValue())); // Handle DNI edit
        probintziaColumn.setOnEditCommit(event -> event.getRowValue().setProbintzia(event.getNewValue())); // Handle Province edit
        pkColumn.setOnEditCommit(event -> event.getRowValue().setPk(event.getNewValue())); // Handle PK edit
        herriaColumn.setOnEditCommit(event -> event.getRowValue().setHerria(event.getNewValue())); // Handle Town edit
        helbideaColumn.setOnEditCommit(event -> event.getRowValue().setHelbidea(event.getNewValue())); // Handle Address edit
        kkColumn.setOnEditCommit(event -> event.getRowValue().setKontuKorrontea(event.getNewValue())); // Handle Account edit
    }

    private void loadLangileakData() {
        langileaList = LangileaDB.getLangileak();
        langileTable.setItems(langileaList);
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
                editButton.setOnAction(event -> saveEditedLangilea(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : new HBox(5, deleteButton, editButton));
            }
        });
    }

    public void updateLangileakTable() {
        langileaList.setAll(LangileaDB.getLangileak());
    }

    private void confirmAndDelete(Langilea langilea) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Está seguro de eliminar este registro?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) deleteLangilea(langilea);
        });
    }

    private void saveEditedLangilea(Langilea langilea) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Desea guardar los cambios?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                LangileaDB.updateLangilea(langilea);
                langileTable.refresh();
            }
        });
    }

    private void deleteLangilea(Langilea langilea) {
        langileaList.remove(langilea);
        LangileaDB.deleteLangilea(langilea.getId());
    }

    @FXML
    public void onLangileakGehituButtonClicked() {
        ShowLangileakGehitu();
    }

    private void ShowLangileakGehitu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/LangileakGehitu.fxml"));
            Scene scene = new Scene(loader.load());
            LangileakGehituController controller = loader.getController();
            controller.setParentController(this);

            Stage stage = new Stage();
            controller.setUsingStage(stage);
            stage.setScene(scene);
            stage.setTitle("Langile Kudeaketa");
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

    @FXML
    public void onAtzeraButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/LehenOrria.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/com/example/gerenteapp/css.css").toExternalForm());
            Stage stage = (Stage) langileTable.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Lehen Orria");
            stage.centerOnScreen();

            String erabiltzaileIzena = lblUser.getText().trim();

            LehenOrriaController controller = loader.getController();
            controller.setErabiltzailea(erabiltzaileIzena);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






