package by.epam.task4.service.sax;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import by.epam.task4.model.*;

public class CustomXMLHandler extends DefaultHandler {
	
	// TODO: modify to handle SAX parsing and build new Medicine object

	private static final Logger LOG = LogManager.getLogger(CustomXMLHandler.class);
	
	private Set<Medicine> medicins;
	private EnumSet<Elements> withContent;
	
	private Elements currentElement;
	
	private Medicine currentMedicine;
	private Version currentVersion;
	private Certificate currentCertificate;
	private Pack currentPackage;
	private Dosage currentDosage;
	
	public CustomXMLHandler() {
		medicins = new HashSet<Medicine>();
		withContent = EnumSet.range(Elements.PHARM, Elements.FREQUENCY);
	}
	
	@Override
	public void startDocument() {
		LOG.info("\nParsing started\n");
	}
	
	@Override
	public void endDocument() {
		LOG.info("\nParsing finished\n");
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attrs) {
		switch (localName) {
			case "Antibiotic":
				currentMedicine = new Antibiotic();
			case "Vitamin":
				currentMedicine = new Vitamin();
			case "Analgetic":
				currentMedicine = new Analgetic();
			case "Pharm":
			case "Version":
			case "Producer":
			case "Form":
			case "Certificate":
			case "RegistredBy":
			case "RegistrationDate":
			case "ExpireDate":
			case "Package":
			case "Quantity":
			case "Price":
			case "Dosage":
			case "Amount":
			case "Frequency":
		}
		
//		String line = "<" + localName;
//		for (int i = 0; i < attrs.getLength(); i++) {
//			line += " " + attrs.getLocalName(i) + " = " + attrs.getValue(i);
//		}
//		line += ">";
//		LOG.info(line.trim());
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) {
		LOG.info("finished element: " + localName + "\n");
		
//		LOG.info("</" + localName + ">");
	}
	
	@Override
	public void characters(char[] ch, int start, int length) {
		
		
		
//		LOG.info(new String(ch, start, length));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void warning(SAXParseException e) {
		LOG.warn(getLineAddress(e), e);
	}
	
	@Override
	public void error(SAXParseException e) {
		LOG.error(getLineAddress(e), e);
	}
	
	@Override
	public void fatalError(SAXParseException e) {
		LOG.fatal(getLineAddress(e), e);
	}
	
	public String getLineAddress(SAXParseException e) {
		return e.getLineNumber() + ":" + e.getColumnNumber();
	}

	public Set<Medicine> getMedicins() {
		return medicins;
	}

	public void setMedicins(Set<Medicine> medicins) {
		this.medicins = medicins;
	}
}
