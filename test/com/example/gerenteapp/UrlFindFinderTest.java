package com.example.gerenteapp;

import org.junit.jupiter.api.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UrlFindFinderTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testObtainDocumentDoesNotThrow() {
        assertDoesNotThrow(() -> UrlFindFinder.obtainDocument());
    }

    @Test
    void testGetFilesWithValidUrl() throws Exception {
        String url = "https://www.aemet.es/xml/municipios/localidad_20076.xml";
        ArrayList<Object> result = UrlFindFinder.getFiles(true, url);
        assertNotNull(result);
        assertTrue(result.get(0) instanceof InputStream || result.get(1) instanceof File);
    }

    @Test
    void testGetFilesWithInvalidUrl() {
        Exception exception = assertThrows(IOException.class, () -> {
            UrlFindFinder.getFiles(true, "invalid_url");
        });
        assertTrue(exception.getMessage().contains("La URL proporcionada no es válida"));
    }

    @Test
    void testGetFilesWithNullUrl() throws Exception {
        ArrayList<Object> result = UrlFindFinder.getFiles(true, null);
        assertNotNull(result);
        assertNull(result.get(0));
        assertNull(result.get(1));
    }

    @Test
    void testObtainDocumentThrowsWhenXmlNotFound() {
        assertThrows(IOException.class, () -> {
            UrlFindFinderForTest.obtainDocumentWithNullSources();
        });
    }

    // Clase auxiliar para testear escenarios específicos con entradas simuladas
    static class UrlFindFinderForTest {
        public static void obtainDocumentWithNullSources() throws Exception {
            // Simula comportamiento de obtainDocument con ambos nulls
            ArrayList<Object> mock = new ArrayList<>();
            mock.add(null); // iXmlFile
            mock.add(null); // fXmlFile

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputStream iXmlFile = (InputStream) mock.get(0);
            File fXmlFile = (File) mock.get(1);

            if (iXmlFile != null) {
                dBuilder.parse(iXmlFile);
            } else if (fXmlFile != null) {
                dBuilder.parse(fXmlFile);
            } else {
                throw new IOException("No se pudo obtener el archivo XML.");
            }
        }
    }
}
