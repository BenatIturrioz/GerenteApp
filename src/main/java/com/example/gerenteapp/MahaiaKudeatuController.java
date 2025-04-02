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


public class MahaiaKudeatuController extends BaseController {

    @FXML
    private Button mahaiaGehituButton;

    @FXML
    private TableView<Mahaia> mahaiaTable;

    @FXML
    private TableColumn<Mahaia, Integer> mahaiaIdColumn;

    @FXML
    private TableColumn<Mahaia, Integer> zenbakiaColumn;

    @FXML
    private TableColumn<Mahaia, Integer> bezeroKopMaxColumn;

    @FXML
    private TableColumn<Mahaia, Void> accionColumn;

    // TableView-erako datuen zerrenda
    private ObservableList<Mahaia> mahaiaList = FXCollections.observableArrayList();
    @FXML
    private Label lblUser;

    void setErabiltzailea(String izena){
        lblUser.setText(izena);
    }
    @FXML
    public void initialize() {
        // Zutabeen konfigurazioa
        mahaiaIdColumn.setCellValueFactory(new PropertyValueFactory<>("mahaia_id"));
        zenbakiaColumn.setCellValueFactory(new PropertyValueFactory<>("zenbakia"));
        bezeroKopMaxColumn.setCellValueFactory(new PropertyValueFactory<>("bezeroKopMax"));

        zenbakiaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        bezeroKopMaxColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));


        mahaiaTable.setEditable(true);

        // MahaiaDB erabiliz datuak lortu datu-basean
        MahaiaDB mahaiaDB = new MahaiaDB();
        mahaiaList = mahaiaDB.getMahaia();

        // Lortutako datuak TableView-era esleitu
        mahaiaTable.setItems(mahaiaList);

        addActionButtonToTable();
    }

    private void addActionButtonToTable() {
        accionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("🗑️");
            private final Button editButton = new Button("💾");

            {
                deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                editButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                deleteButton.setOnAction(event -> {
                    Mahaia mahaia = getTableView().getItems().get(getIndex());
                    confirmAndDelete(mahaia);
                });

                editButton.setOnAction(event -> {
                    Mahaia mahaia = getTableView().getItems().get(getIndex());
                    saveEditedMahaia(mahaia);
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

    private void confirmAndDelete(Mahaia mahaia) {
        // Baieztapen elkarrizketa-koadroa erakutsi
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Ezabatzea baieztatu");
        alert.setHeaderText("Ziur zaude mahaia hau ezabatu nahi duzula?");
        alert.setContentText("Mahaia ID: " + mahaia.getMahaia_id());

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteMahaia(mahaia);
            }
        });
    }

    private void saveEditedMahaia(Mahaia mahaia) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Aldaketak gordetzea baieztatu");
        alert.setHeaderText("Ziur zaude aldaketak gorde nahi dituzula?");
        alert.setContentText("Mahaia ID: " + mahaia.getMahaia_id());

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                MahaiaDB.updateMahaia(mahaia);
                mahaiaTable.refresh();
                System.out.println("Mahaia eguneratuta: " + mahaia);
            }
        });
    }

    private void deleteMahaia(Mahaia mahaia) {
        mahaiaList.remove(mahaia);
        MahaiaDB.deleteMahaia(mahaia.getMahaia_id());
        System.out.println("Mahaia ezabatuta: " + mahaia);
    }

    @FXML
    public void onMahaiaGehituButtonClicked() {
        ShowMahaiaGehitu();
    }

    @FXML
    public void onAtzeraButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/LehenOrria.fxml"));
            Scene escenaAnterior = new Scene(loader.load());

            escenaAnterior.getStylesheets().add(getClass().getResource("/com/example/gerenteapp/css.css").toExternalForm());
            Stage currentStage = (Stage) mahaiaTable.getScene().getWindow();

            currentStage.setScene(escenaAnterior);
            currentStage.setTitle("Lehen Orria");

            currentStage.centerOnScreen();

            String erabiltzaileIzena = lblUser.getText().trim();

            LehenOrriaController controller = loader.getController();
            controller.setErabiltzailea(erabiltzaileIzena);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ShowMahaiaGehitu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gerenteapp/MahaiakGehitu.fxml"));
            Scene escenaMahaiaGehitu = new Scene(loader.load());
            MahaiakGehituController controller = loader.getController();
            controller.setParentController(this);
            Stage nuevoStage = new Stage();
            controller.setUsingStage(nuevoStage);
            nuevoStage.setScene(escenaMahaiaGehitu);
            nuevoStage.setTitle("Mahaia Kudeaketa");
            nuevoStage.setWidth(670);  // Zabalera nahi bezala ezarri
            nuevoStage.setHeight(460); // Altuera nahi bezala ezarri
            nuevoStage.centerOnScreen();
            nuevoStage.show();

            String erabiltzaileIzena = lblUser.getText().trim();

            controller.setErabiltzailea(erabiltzaileIzena);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateMahaiaTable() {
        ObservableList<Mahaia> updatedMahaiaList = MahaiaDB.getMahaia();
        mahaiaList.setAll(updatedMahaiaList); // Uneko elementuak ordezkatu
    }
}

