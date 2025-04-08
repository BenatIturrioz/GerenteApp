package com.example.gerenteapp;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UrlFindFinder {

    public static void obtainDocument() throws ParserConfigurationException, NoSuchAlgorithmException, IOException, KeyManagementException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = null;

        String url = "https://www.aemet.es/xml/municipios/localidad_20076.xml";

        ArrayList<Object> a = getFiles(true, url);

        InputStream iXmlFile = (InputStream) a.get(0);
        File fXmlFile = (File) a.get(1);

        if (iXmlFile != null) {
            document = dBuilder.parse(iXmlFile);
        } else if (fXmlFile != null) {
            document = dBuilder.parse(fXmlFile);
        } else {
            throw new IOException("No se pudo obtener el archivo XML.");
        }

        document.getDocumentElement().normalize();


        XMLTransformerXPath.transformXML(document);

        System.out.println("Archivo XML descargado y procesado correctamente.");
    }


    public static ArrayList<Object> getFiles(boolean aukeratu, String url)
            throws KeyManagementException, NoSuchAlgorithmException {

        File fXmlFile = null;
        InputStream iXmlFile = null;
        ArrayList<Object> result = new ArrayList<>();

        if (url != null && !url.isEmpty()) {
            try {
                aukeratu = false;
                MyUrlConnection.disableSSLCertificateValidation();
                iXmlFile = MyUrlConnection.getFileFromURL(url);
            } catch (MalformedURLException e) {
                showError("La URL proporcionada no es válida.");
                return new ArrayList<>() {{
                    add(null);
                    add(null);
                }};
            } catch (IOException e) {
                showError("Ezin izan duzu xml deskargatu, mesedez, begiratu ea internetera konexioa duzun.");
                return new ArrayList<>() {{
                    add(null);
                    add(null);
                }};
            }
        } else {
            result.add(null);
            result.add(null);
            return result;
        }

        result.add(iXmlFile);
        result.add(fXmlFile);
        return result;
    }

    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
