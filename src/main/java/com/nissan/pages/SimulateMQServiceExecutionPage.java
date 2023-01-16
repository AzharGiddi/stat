package com.nissan.pages;

import java.io.File;
import java.io.StringWriter;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;
import org.w3c.dom.Document;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

/***
 * 
 * @author AB00789853
 *
 */
public class SimulateMQServiceExecutionPage extends BasePage {

	@FindBy(xpath = "//*[@id='paramTitle' and text()='Execute STATServicePackage.Services.CreateUpdateRepairOrder']")
	public ExtWebElement textHeader;

	@FindBy(xpath = "//td[text()='allchars']/following-sibling::td//input")
	public ExtWebElement textBoxAllChars;

	@FindBy(xpath = "//button[@title='Execute']")
	public ExtWebElement buttonExecute;

	public void waitForPageToLoad() {

		try {
		//getWindow();
		textHeader.waitForPresent();
		//textHeader.isDisplayed();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(
					this.getClass().getSimpleName()+" did not load, Waited 20 seconds for the visibility of the element located by xpath: "
							+ textHeader.toString(),
					e));

		}

	}
	
	public void waitForPresent() {
		
		
	}

	public void getWindow(String window) {

		Set<String> whs = DriverManager.getDriver().getWindowHandles();
		for (String wh : whs) {
			if(wh.equals(window))
				continue;
			DriverManager.getDriver().switchTo().window(wh);
			try {
			if (textHeader.isDisplayed()) {
					return;

				}
			}catch(NoSuchElementException e) {
				
				continue;
				
			}
			

		}
	}
	
	public void getWindow(int s) {
		DriverManager.getDriver().switchTo().defaultContent();
		Set<String> whs = DriverManager.getDriver().getWindowHandles();
		while (whs.iterator().hasNext()) {

			DriverManager.getDriver().switchTo().window(whs.iterator().next());
			if (buttonExecute.isPresent()) {
				return;

			}

		}
	}

	public void sendROData(String xmlFileName) {

		final String xmlFilePath = System.getProperty("user.dir") + "/resources/roxmls/" + xmlFileName + ".txt";

		Document xmlDocument = convertXMLFileToXMLDocument(xmlFilePath);

		textBoxAllChars.clearAndSendKeys(convertXMLToString(xmlDocument));

	}
	
	public String getRODataString(String xmlFileName) {

		final String xmlFilePath = System.getProperty("user.dir") + "/resources/roxmls/" + xmlFileName + ".txt";

		Document xmlDocument = convertXMLFileToXMLDocument(xmlFilePath);

		return convertXMLToString(xmlDocument);

	}

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

	public String convertXMLToString(Document xmlDocument) {

		// String xmlPath =
		// System.getProperty("user.dir")+"/resources/roxmls/"+xmlName+".txt";

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		String str = "";

		try {
			transformer = tf.newTransformer();

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

	public SimulateMQServiceExecutionPage() {
		ElementFactory.initElements(DriverManager.getDriver(), this);
	}
}
