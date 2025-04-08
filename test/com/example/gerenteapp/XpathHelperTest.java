package com.example.gerenteapp;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpressionException;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class XpathHelperTest {

    private Document loadXML(String xmlContent) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void get_validExpression_shouldReturnNodes() throws Exception {
        String xml = """
                <root>
                    <item>1</item>
                    <item>2</item>
                </root>
                """;
        Document doc = loadXML(xml);
        NodeList nodes = XpathHelper.get(doc, "//item");
        assertEquals(2, nodes.getLength());
    }

    @Test
    void get_invalidExpression_shouldThrowException() throws Exception {
        String xml = "<root><item>1</item></root>";
        Document doc = loadXML(xml);

        assertThrows(XPathExpressionException.class, () -> {
            XpathHelper.get(doc, "///item"); // expresión malformada
        });
    }

    @Test
    void delete_existingNodes_shouldRemoveThem() throws Exception {
        String xml = """
                <root>
                    <item>1</item>
                    <item>2</item>
                </root>
                """;
        Document doc = loadXML(xml);
        Document resultDoc = XpathHelper.delete(doc, "//item");

        NodeList items = resultDoc.getElementsByTagName("item");
        assertEquals(0, items.getLength()); // todos eliminados
    }

    @Test
    void delete_noMatchingNodes_shouldDoNothing() throws Exception {
        String xml = "<root><other>data</other></root>";
        Document doc = loadXML(xml);

        Document resultDoc = XpathHelper.delete(doc, "//nonexistent");
        NodeList others = resultDoc.getElementsByTagName("other");
        assertEquals(1, others.getLength()); // nada eliminado
    }

    @Test
    void delete_invalidExpression_shouldThrowException() throws Exception {
        String xml = "<root><item>1</item></root>";
        Document doc = loadXML(xml);

        assertThrows(XPathExpressionException.class, () -> {
            XpathHelper.delete(doc, "///item"); // mal formada
        });
    }
}
