package com.example.gerenteapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UrlFindFinderTest {
    private File tempXmlFile;

    @BeforeEach
    void setUp() throws IOException {
        tempXmlFile = File.createTempFile("test", ".xml");
        try (FileWriter writer = new FileWriter(tempXmlFile)) {
            writer.write("<root><test>data</test></root>");
        }
    }

    @AfterEach
    void tearDown() {
        tempXmlFile.delete();
    }

    @Test
    void testObtainDocument() {
        assertDoesNotThrow(() -> UrlFindFinder.obtainDocument());
    }

    @Test
    void testGetFilesWithValidUrl() throws KeyManagementException, NoSuchAlgorithmException, IOException {
        String url = "https://www.aemet.es/xml/municipios/localidad_20076.xml";
        ArrayList<Object> result = UrlFindFinder.getFiles(true, url);
        assertNotNull(result);
        assertTrue(result.get(0) instanceof InputStream || result.get(1) instanceof File);
    }

    @Test
    void getFilesWithInvalidUrl() {
        Exception exception = assertThrows(IOException.class, () -> {
            UrlFindFinder.getFiles(true, "invalid-url");
        });

        assertTrue(exception.getMessage().contains("La URL proporcionada no es válida"));
    }
}
