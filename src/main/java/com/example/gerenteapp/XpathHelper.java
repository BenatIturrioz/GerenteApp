package com.example.gerenteapp;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;

public class XpathHelper {

    public static Document delete(Document doc, String expression) throws XPathExpressionException {
        NodeList nodeListToDelete = get(doc, expression);
        for (int i = 0; i < nodeListToDelete.getLength(); i++) {
            Node node = nodeListToDelete.item(i);
            node.getParentNode().removeChild(node);
        }
        return doc;
    }

    public static NodeList get(Document doc, String expression) throws XPathExpressionException {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        XPathExpression xPathExpression = xPath.compile(expression);
        NodeList nodeList = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);
        return nodeList;
    }

}
