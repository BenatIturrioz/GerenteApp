package com.example.gerenteapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
    private Button GuardarCambiosButton;

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
        izenaColumn.setOnEditCommit(event -> {
            Langilea langilea = event.getRowValue();
            langilea.setIzena(event.getNewValue());
            enableSaveButton();
        });

        abizenaColumn.setOnEditCommit(event -> {
            Langilea langilea = event.getRowValue();
            langilea.setAbizena(event.getNewValue());
            enableSaveButton();
        });

        emailaColumn.setOnEditCommit(event -> {
            Langilea langilea = event.getRowValue();
            langilea.setEmaila(event.getNewValue());
            enableSaveButton();
        });

        telfColumn.setOnEditCommit(event -> {
            Langilea langilea = event.getRowValue();
            langilea.setTelf(event.getNewValue());
            enableSaveButton();
        });

        // Cargar datos desde LangileaDAO
        LangileaDAO langileaDAO = new LangileaDAO();
        langileaList = langileaDAO.getLangileak();
        langileTable.setItems(langileaList);
    }

    private void enableSaveButton() {
        GuardarCambiosButton.setDisable(false);
    }

    @FXML
    public void onGuardarCambiosButtonClicked() {
        LangileaDAO langileaDAO = new LangileaDAO();
        for (Langilea langilea : langileaList) {
            langileaDAO.updateLangilea(langilea);
        }
        GuardarCambiosButton.setDisable(true);
        System.out.println("Cambios guardados correctamente.");
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

    public void updateLangileakTable() {
        LangileaDAO langileaDAO = new LangileaDAO();
        ObservableList<Langilea> updatedLangileaList = langileaDAO.getLangileak();
        langileaList.setAll(updatedLangileaList);
    }
}

