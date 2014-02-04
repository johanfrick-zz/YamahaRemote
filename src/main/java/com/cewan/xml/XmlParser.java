package com.cewan.xml;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.StringReader;

public class XmlParser {

    static String parseXml(String xml, String expression) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            Document doc = builder.parse(is);
            XPathFactory xpathfactory = XPathFactory.newInstance();
            XPath xpath = xpathfactory.newXPath();
            XPathExpression expr = xpath.compile(expression);
            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;
            return nodes.item(0).getNodeValue();
        } catch (Exception e) {
            return null;
        }
    }
}
