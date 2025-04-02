package com.example.gerenteapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws ParserConfigurationException, NoSuchAlgorithmException, IOException, KeyManagementException, SAXException {

        UrlFindFinder.obtainDocument();
        UploadToDrive.uploadFile();
    }
}