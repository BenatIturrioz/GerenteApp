package com.example.gerenteapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
            ErreserbakGehituController controller = loader.getController();
            controller.setParentController(this);
            Stage nuevoStage = new Stage();
            controller.setUsingStage(nuevoStage);
            nuevoStage.setScene(escenaErreserbaGehitu);
            nuevoStage.setTitle("Erreserba Kudeaketa");
            nuevoStage.setWidth(670);  // Establece el ancho deseado
            nuevoStage.setHeight(460); // Establece la altura deseada
            nuevoStage.centerOnScreen();
            nuevoStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void updateErreserbakTable() {
        // Obtener los datos actualizados desde la base de datos
        ErreserbaDAO erreserbaDAO = new ErreserbaDAO();
        ObservableList<Erreserba> updatedErreserbakList = erreserbaDAO.getErreserbak();

        // Actualizar los datos del TableView
        erreserbaList.setAll(updatedErreserbakList); // Reemplazar los elementos actuales
    }
}












