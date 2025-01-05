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

public class ErreserbakKudeatuController extends BaseController {

    public Button ErreserbaGehituButton;

    @FXML
    private TableView<Erreserba> erreserbaTable;

    @FXML
    private TableColumn<Erreserba, Integer> idColumn;

    @FXML
    private TableColumn<Erreserba, Integer> erreserbaIdColumn;

    @FXML
    private TableColumn<Erreserba, String> mahaiaIdColumn;

    @FXML
    private TableColumn<Erreserba, Integer> langileaIdColumn;

    @FXML
    private TableColumn<Erreserba, String> bezeroIzenaColumn;

    @FXML
    private TableColumn<Erreserba, String> telfColumn;

    @FXML
    private TableColumn<Erreserba, LocalDate> dataColumn;

    @FXML
    private TableColumn<Erreserba, String> bezroKopColumn;

    @FXML
    private TableColumn<Erreserba, Void> accionColumn;

    // TableView-erako datuen zerrenda
    private ObservableList<Erreserba> erreserbaList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Zutabeen konfigurazioa
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        erreserbaIdColumn.setCellValueFactory(new PropertyValueFactory<>("erreserba_id"));
        mahaiaIdColumn.setCellValueFactory(new PropertyValueFactory<>("mahaia_id"));
        langileaIdColumn.setCellValueFactory(new PropertyValueFactory<>("langilea_id"));
        bezeroIzenaColumn.setCellValueFactory(new PropertyValueFactory<>("bezeroIzena"));
        telfColumn.setCellValueFactory(new PropertyValueFactory<>("telf"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        bezroKopColumn.setCellValueFactory(new PropertyValueFactory<>("bezroKop"));

        bezeroIzenaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        telfColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        mahaiaIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        bezroKopColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        erreserbaTable.setEditable(true);

        // ErreserbaDAO erabiliz datuak lortu datu-basean
        ErreserbaDAO erreserbaDAO = new ErreserbaDAO();
        erreserbaList = erreserbaDAO.getErreserbak();

        // Lortutako datuak TableView-era esleitu
        erreserbaTable.setItems(erreserbaList);

        addActionButtonToTable();
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

        mahaiaIdColumn.setOnEditCommit(event -> {
            Erreserba erreserba = event.getRowValue();
            erreserba.setMahaia_id(event.getNewValue());
        });

        bezroKopColumn.setOnEditCommit(event -> {
            Erreserba erreserba = event.getRowValue();
            erreserba.setBezroKop(event.getNewValue());
        });
    }

    private void addActionButtonToTable() {
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

    private void confirmAndDelete(Erreserba erreserba) {
        // Baieztapen elkarrizketa-koadroa erakutsi
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Ezabatzea baieztatu");
        alert.setHeaderText("Ziur zaude erregistro hau ezabatu nahi duzula?");
        alert.setContentText("Erregistroa: " + erreserba.getBezeroIzena());

        // Erabiltzailearen erantzuna itxaron
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteerreserba(erreserba);
            }
        });
    }

    private void saveEditedErreserba(Erreserba erreserba) {
        // Gorde aurretik baieztapen elkarrizketa-koadroa erakutsi
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Gordetzea baieztatu");
        alert.setHeaderText("Ziur zaude aldaketak gorde nahi dituzula?");
        alert.setContentText("Erregistroa: " + erreserba.getBezeroIzena());

        // Erabiltzailearen erantzuna itxaron
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Aldaketak datu-basean gorde
                ErreserbaDAO erreserbaDAO = new ErreserbaDAO();
                erreserbaDAO.updateErreserba(erreserba);

                // Taula freskatu aldaketak erakusteko
                erreserbaTable.refresh();

                System.out.println("Erregistroa eguneratuta: " + erreserba);
            }
        });
    }

    private void deleteerreserba(Erreserba erreserba) {
        // Ikus daitezkeen zerrendatik ezabatu
        erreserbaList.remove(erreserba);

        // Datu-basean eguneratu
        LangileaDAO langileaDAO = new LangileaDAO();
        langileaDAO.deleteLangilea(erreserba.getId());

        System.out.println("Erregistroa ezabatuta: " + erreserba);
    }

    @FXML
    public void onErreserbaGehituButtonClicked() {
        ShowErreserbaGehitu();
    }

    @FXML
    public void onAtzeraButtonClicked() {
        try {
            // Aurreko leihoaren FXML fitxategia kargatu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/LehenOrria.fxml"));
            Scene escenaAnterior = new Scene(loader.load());

             escenaAnterior.getStylesheets().add(getClass().getResource("/com/example/gerenteapp/css.css").toExternalForm());
            // Uneko Stage-a lortu
            Stage currentStage = (Stage) erreserbaTable.getScene().getWindow();

            // Eszena uneko Stage-an konfiguratu
            currentStage.setScene(escenaAnterior);
            currentStage.setTitle("Lehen Orria");

            // Hautazkoa: leihoa erdian kokatu
            currentStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ShowErreserbaGehitu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/ErreserbakGehitu.fxml"));
            Scene escenaErreserbaGehitu = new Scene(loader.load());
            ErreserbakGehituController controller = loader.getController();
            controller.setParentController(this);
            Stage nuevoStage = new Stage();
            controller.setUsingStage(nuevoStage);
            nuevoStage.setScene(escenaErreserbaGehitu);
            nuevoStage.setTitle("Erreserba Kudeaketa");
            nuevoStage.setWidth(670);  // Zabalera nahi bezala ezarri
            nuevoStage.setHeight(460); // Altuera nahi bezala ezarri
            nuevoStage.centerOnScreen();
            nuevoStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateErreserbakTable() {
        // Eguneratutako datuak datu-basetik lortu
        ErreserbaDAO erreserbaDAO = new ErreserbaDAO();
        ObservableList<Erreserba> updatedErreserbakList = erreserbaDAO.getErreserbak();

        // TableView-eko datuak eguneratu
        erreserbaList.setAll(updatedErreserbakList); // Uneko elementuak ordezkatu
    }
}













