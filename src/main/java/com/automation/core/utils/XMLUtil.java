package com.automation.core.utils;

import java.io.File;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class XMLUtil {

	private XMLUtil() {

	}
	/**
	 * Converts XMLFile into XMLDocument
	 * @param filePath Absolute path to the xmlfile.
	 * @return XMLDocument
	 */
	private static Document convertXMLFileToXMLDocument(String filePath) {
		// Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try {
			// Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			// Parse the content to Document object
			Document xmlDocument = builder.parse(new File(filePath));

			return xmlDocument;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Converts XMLDocument to String
	 * @param xmlDocument
	 * @return xmlDocument converted to String
	 */
	private static String convertXMLToString(Document xmlDocument) {

		TransformerFactory factory = TransformerFactory.newInstance();
		// to be compliant, prohibit the use of all protocols by external entities:
		//factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		//factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

		Transformer transformer;
		String str = "";

		try {
			transformer = factory.newTransformer();

			// Uncomment if you do not require XML declaration
			// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

			// A character stream that collects its output in a string buffer,
			// which can then be used to construct a string.
			StringWriter writer = new StringWriter();

			// transform document to string
			transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));

			String xmlString = writer.getBuffer().toString();
		//	System.out.println(xmlString); // Print to console or logs

			str = xmlString;
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;
	}
	
	public static String getXMLString(String xmlPath) {
		
	return convertXMLToString(convertXMLFileToXMLDocument(xmlPath));
		
	}
}
