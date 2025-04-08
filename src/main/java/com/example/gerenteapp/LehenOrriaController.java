package com.example.gerenteapp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LehenOrriaController extends BaseController {

    @FXML
    private Button langileakButton;

    @FXML
    private Button erreserbakButton;

    @FXML
    private Button mahaiakButton;

    @FXML
    private Button chatIrekiButton;

    @FXML
    private Label eguraldiaLabel;

    @FXML
    private Button erabiltzaileaKudeatuButton;

    @FXML
    private Button eguraldiaButton;

    @FXML
    private Button saioaItxiButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Label lblUser;

    void setErabiltzailea(String izena){
        lblUser.setText(izena);
    }

    /**
     * "Langileak" botoian klik egitean gertatzen dena.
     */
    @FXML
    private void onLangileakButtonClick() {
        aldatuEscena("/com/example/gerenteapp/LangileakKudeatu.fxml",
                "Langile Kudeaketa", langileakButton);
    }

    /**
     * "Erreserbak" botoian klik egitean gertatzen dena.
     */
    @FXML
    private void onErreserbakButtonClick() {
        aldatuEscena("/com/example/gerenteapp/ErreserbakKudeatu.fxml",
                "Erreserba Kudeaketa", erreserbakButton);
    }

    @FXML
    private void onEguraldiaButtonClick() {
        try {
            // 1. Llama a tu función existente en HelloController
            new HelloController().onHelloButtonClick();

            // 2. Muestra la alerta de confirmación
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("Eguraldia deskargatuta!");
            alert.showAndWait();

        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Error al descargar: " + e.getMessage());
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void onJReportButtonClick() {
        try {
            String fullName = "Unai, Ander & Beñat";

            // Rutas de los archivos
            String jrxmlPath = "src/main/resources/com/example/gerenteapp/chartToReplace.jrxml";

            //Aldagaia zerrendan sartu. Gehiago badaude, gehiago gehitu beharko dira.
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("firstName", fullName);

            //Zerrenda hartu DBtik
            List<ErreserbaJasper> listaDeProductos = ErreserbaJasper.loadErreserbaList();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaDeProductos);
            parameters.put("ProduktuaDataSet", dataSource);

            List<ErreserbaChart> productListForChart = ErreserbaChart.loadErreserbaList();
            JRBeanCollectionDataSource dataSourceForChart = new JRBeanCollectionDataSource(productListForChart);
            parameters.put("MotaDataSource", dataSourceForChart);

            //Konpilatu .jrxml .jasper fitxategi batera
            String jasperPath = "src/main/resources/com/example/gerenteapp/produktuaReport.jasper";
            JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new File(jasperPath));

            // Report-a osatu (DBko konexiorik gabe, soilik parametroak)
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            String home = System.getProperty("user.home");
            String outputPath = home + "/Desktop/2ERRONKA_JatetxeInformea_2Taldea.pdf";

            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);

            System.out.println("Reporte generado en: " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onChatIrekiButtonClick() {
        aldatuEscena("/com/example/gerenteapp/Chat.fxml",
                "Chat Ireki Kudeaketa", chatIrekiButton);
    }

    @FXML
    private void onMahaiakButtonClick() {
        aldatuEscena("/com/example/gerenteapp/MahaiakKudeatu.fxml",
                "Mahai kudeaketa", mahaiakButton);
    }

    @FXML
    private void onSaioaItxiButtonClick() {
        aldatuEscena("/com/example/gerenteapp/SaioHasiera.fxml",
                "Saioa hasi", saioaItxiButton);
    }

    @FXML
    private void onErabiltzaileaKudeatuButtonClick() {
        aldatuEscena("/com/example/gerenteapp/ErabiltzaileakKudeatu.fxml",
                "Erabiltzaile kudeaketa", erabiltzaileaKudeatuButton);
    }

    /**
     * Eszena aldatu emandako datuekin.
     *
     * @param fxmlPath Eszenaren FXML fitxategiaren bidea.
     * @param izenburua Leiho berriaren izenburua.
     * @param button Botoia, uneko Stage-a eskuratzeko.
     */
    private void aldatuEscena(String fxmlPath, String izenburua, Button button) {
        ErabiltzaileaDB lk = new ErabiltzaileaDB();
        boolean baimena = lk.baimenaTxat(lblUser.getText());
        String erabiltzaileIzena = lblUser.getText().trim();
        try {
            if (izenburua.equals("Chat Ireki Kudeaketa")) {
                if (baimena) {
                    FXMLLoader load = new FXMLLoader(getClass().getResource(fxmlPath));
                    Scene eszenaBerria = new Scene(load.load());

                    eszenaBerria.getStylesheets().add(getClass().getResource("/com/example/gerenteapp/css.css").toExternalForm());

                    Stage oraingoStagea = (Stage) button.getScene().getWindow();

                    oraingoStagea.setScene(eszenaBerria);
                    oraingoStagea.setTitle(izenburua);
                    oraingoStagea.centerOnScreen();

                    ChatClientController controller = load.getController();
                    controller.setErabiltzailea(erabiltzaileIzena);
                } else {
                    erakutsiErrorea("Ez daukazu baimenik txatean sartzeko");
                }
            }else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Scene eszenaBerria = new Scene(loader.load());

                eszenaBerria.getStylesheets().add(getClass().getResource("/com/example/gerenteapp/css.css").toExternalForm());

                Stage oraingoStagea = (Stage) button.getScene().getWindow();

                oraingoStagea.setScene(eszenaBerria);
                oraingoStagea.setTitle(izenburua);
                oraingoStagea.centerOnScreen();
                if(izenburua.equals("Langile Kudeaketa")) {
                    LangileakKudeatuController controller = loader.getController();
                    controller.setErabiltzailea(erabiltzaileIzena);
                }else if(izenburua.equals("Erreserba Kudeaketa")) {
                    ErreserbakKudeatuController controller = loader.getController();
                    controller.setErabiltzailea(erabiltzaileIzena);
                }else if(izenburua.equals("Mahai kudeaketa")) {
                    MahaiaKudeatuController controller = loader.getController();
                    controller.setErabiltzailea(erabiltzaileIzena);
                }else if(izenburua.equals("Erabiltzaile kudeaketa")) {
                    ErabiltzaileakKudeatuController controller = loader.getController();
                    controller.setErabiltzailea(erabiltzaileIzena);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            erakutsiErrorea("Orria ezin izan da kargatu :(");
        }
    }
    /**
     * Errore-mezua erakutsi etiketan.
     *
     * @param mezua Errore-mezua.
     */
    private void erakutsiErrorea(String mezua) {
        errorLabel.setText(mezua);
        errorLabel.setVisible(true);
    }

    public Button getEguraldiaButton() {
        return eguraldiaButton;
    }

    public void setEguraldiaButton(Button eguraldiaButton) {
        this.eguraldiaButton = eguraldiaButton;
    }

    public Label getEguraldiaLabel() {
        return eguraldiaLabel;
    }

    public void setEguraldiaLabel(Label eguraldiaLabel) {
        this.eguraldiaLabel = eguraldiaLabel;
    }
}


