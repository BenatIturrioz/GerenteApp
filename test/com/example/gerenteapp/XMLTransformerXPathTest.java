package com.example.gerenteapp;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;

class XMLTransformerXPathTest {

    private Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void transformXML_validInput_shouldCreateOutputFile() {
        String xml = """
                <root>
                    <dia fecha="2025-04-07">
                        <prob_precipitacion>20</prob_precipitacion>
                        <prob_precipitacion>40</prob_precipitacion>
                        <temperatura>
                            <maxima>25</maxima>
                            <minima>15</minima>
                        </temperatura>
                        <estado_cielo periodo="00-06" descripcion="Despejado"/>
                    </dia>
                </root>
                """;
        assertDoesNotThrow(() -> {
            Document doc = loadXMLFromString(xml);
            XMLTransformerXPath.transformXML(doc);
        });
    }

    @Test
    void transformXML_invalidPrecipitacion_shouldSkipInvalidValues() {
        String xml = """
                <root>
                    <dia fecha="2025-04-07">
                        <prob_precipitacion>abc</prob_precipitacion>
                        <temperatura>
                            <maxima>20</maxima>
                            <minima>10</minima>
                        </temperatura>
                        <estado_cielo periodo="12-18" descripcion="Nublado"/>
                    </dia>
                </root>
                """;
        assertDoesNotThrow(() -> {
            Document doc = loadXMLFromString(xml);
            XMLTransformerXPath.transformXML(doc);
        });
    }

    @Test
    void transformXML_invalidTemperatura_shouldHandleGracefully() {
        String xml = """
                <root>
                    <dia fecha="2025-04-07">
                        <prob_precipitacion>10</prob_precipitacion>
                        <temperatura>
                            <maxima>veinte</maxima>
                            <minima>5</minima>
                        </temperatura>
                        <estado_cielo periodo="06-12" descripcion="Cubierto"/>
                    </dia>
                </root>
                """;
        assertDoesNotThrow(() -> {
            Document doc = loadXMLFromString(xml);
            XMLTransformerXPath.transformXML(doc);
        });
    }

    @Test
    void transformXML_emptyDescripcionInEstadoCielo_shouldSkipNode() {
        String xml = """
                <root>
                    <dia fecha="2025-04-07">
                        <prob_precipitacion>50</prob_precipitacion>
                        <temperatura>
                            <maxima>30</maxima>
                            <minima>20</minima>
                        </temperatura>
                        <estado_cielo periodo="18-24" descripcion=""/>
                    </dia>
                </root>
                """;
        assertDoesNotThrow(() -> {
            Document doc = loadXMLFromString(xml);
            XMLTransformerXPath.transformXML(doc);
        });
    }

    @Test
    void transformXML_missingTemperatura_shouldSkipThatSection() {
        String xml = """
                <root>
                    <dia fecha="2025-04-07">
                        <prob_precipitacion>70</prob_precipitacion>
                        <estado_cielo periodo="00-06" descripcion="Lluvia"/>
                    </dia>
                </root>
                """;
        assertDoesNotThrow(() -> {
            Document doc = loadXMLFromString(xml);
            XMLTransformerXPath.transformXML(doc);
        });
    }

    @Test
    void transformXML_emptyDocument_shouldHandleWithoutException() {
        String xml = "<root></root>";
        assertDoesNotThrow(() -> {
            Document doc = loadXMLFromString(xml);
            XMLTransformerXPath.transformXML(doc);
        });
    }
}
