package com.example.gerenteapp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
public class XMLTransformerXPath {

    public static void transformXML(Document doc) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document newDoc = builder.newDocument();
            Element root = newDoc.createElement("datos");
            newDoc.appendChild(root);

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            XPathExpression expr = xpath.compile("//dia[prob_precipitacion and temperatura and estado_cielo]");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element diaElement = (Element) nodeList.item(i);
                Element newDia = newDoc.createElement("dia");

                String fecha = diaElement.getAttribute("fecha");
                Element newFecha = newDoc.createElement("fecha");
                newFecha.setTextContent(fecha);
                newDia.appendChild(newFecha);


                NodeList probNodes = diaElement.getElementsByTagName("prob_precipitacion");
                if (probNodes.getLength() > 0) {
                    double probSum = 0;
                    int probCount = 0;
                    for (int j = 0; j < probNodes.getLength(); j++) {
                        String probValue = probNodes.item(j).getTextContent().trim();
                        if (!probValue.isEmpty()) {
                            try {
                                probSum += Double.parseDouble(probValue);
                                probCount++;
                            } catch (NumberFormatException e) {
                                System.out.println("Valor inválido en prob_precipitacion: " + probValue);
                            }
                        }
                    }
                    if (probCount > 0) {
                        double probAverage = probSum / probCount;
                        Element newProb = newDoc.createElement("prob_precipitacion_media");
                        newProb.setTextContent(String.valueOf(probAverage));
                        newDia.appendChild(newProb);
                    }
                }

                NodeList tempNodes = diaElement.getElementsByTagName("temperatura");
                if (tempNodes.getLength() > 0) {
                    Element tempElement = (Element) tempNodes.item(0);

                    String maximaStr = tempElement.getElementsByTagName("maxima").item(0).getTextContent().trim();
                    String minimaStr = tempElement.getElementsByTagName("minima").item(0).getTextContent().trim();

                    try {
                        double maxima = Double.parseDouble(maximaStr);
                        double minima = Double.parseDouble(minimaStr);
                        double tempMedia = (maxima + minima) / 2;

                        Element newTemp = newDoc.createElement("temperatura_media");
                        newTemp.setTextContent(String.valueOf(tempMedia));
                        newDia.appendChild(newTemp);

                    } catch (NumberFormatException e) {
                        System.out.println("Error al convertir temperatura máxima/mínima: " + maximaStr + ", " + minimaStr);
                    }
                }


                NodeList estadoCieloNodes = diaElement.getElementsByTagName("estado_cielo");
                if (estadoCieloNodes.getLength() > 0) {
                    for (int j = 0; j < estadoCieloNodes.getLength(); j++) {
                        Element estadoCieloElement = (Element) estadoCieloNodes.item(j);
                        String periodo = estadoCieloElement.getAttribute("periodo");
                        String descripcion = estadoCieloElement.getAttribute("descripcion");

                        if (!descripcion.isEmpty()) {
                            Element newEstadoCielo = newDoc.createElement("estado_cielo");
                            if(!periodo.isEmpty()) {
                                newEstadoCielo.setAttribute("periodo", periodo);
                            }
                            newEstadoCielo.setTextContent(descripcion);
                            newDia.appendChild(newEstadoCielo);
                        }
                    }
                }

                root.appendChild(newDia);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(newDoc);
            StreamResult result = new StreamResult(new File("../../EguraldiaXML/output.xml"));
            transformer.transform(source, result);

            System.out.println("Archivo XML transformado correctamente y guardado como 'output.xml'");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
