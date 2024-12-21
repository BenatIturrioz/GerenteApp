package com.example.gerenteapp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    // Lista de datos para el TableView
    private ObservableList<Langilea> langileaList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configuración de las columnas
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
        kkColumn.setCellValueFactory(new PropertyValueFactory<>("kk"));
        jaiotzeDataColumn.setCellValueFactory(new PropertyValueFactory<>("jaiotzeData"));

        // Agregar datos de ejemplo
        langileaList.add(new Langilea(1, "12345678A", "Jon", "Perez", "Bizkaia", "48001", "Bilbao",
                "Calle Mayor 10", "jon.perez@email.com", "123456789", "ES1234567890123456789012", LocalDate.of(1990, 1, 1)));

        langileaList.add(new Langilea(2, "87654321B", "Amaia", "Lopez", "Gipuzkoa", "20001", "Donostia",
                "Calle Zurriola 5", "amaia.lopez@email.com", "987654321", "ES9876543210987654321098", LocalDate.of(1985, 5, 15)));

        // Asignar datos al TableView
        langileTable.setItems(langileaList);
    }

}
