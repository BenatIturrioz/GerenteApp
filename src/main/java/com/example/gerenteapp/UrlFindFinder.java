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
            throws KeyManagementException, NoSuchAlgorithmException, MalformedURLException, IOException {
        File fXmlFile = null;
        InputStream iXmlFile = null;

        if (url != null) {
            aukeratu = false;
            MyUrlConnection.disableSSLCertificateValidation();
            iXmlFile = MyUrlConnection.getFileFromURL(url);
        } else if (fXmlFile == null) {
           Alert alert = new Alert(AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText("No se pudo obtener el archivo XML.");
           alert.setContentText("Error no se pudo obtener el enlace");
           alert.showAndWait();
        }

        ArrayList<Object> a = new ArrayList<>();
        a.add(iXmlFile);
        a.add(fXmlFile);

        return a;
    }

}
