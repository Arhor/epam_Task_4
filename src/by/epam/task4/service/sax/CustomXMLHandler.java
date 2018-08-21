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
import by.epam.task4.model.factory.MedicineFactory;

public class CustomXMLHandler extends DefaultHandler {
	
	// TODO: modify to handle SAX parsing and build new Medicine object

	private static final Logger LOG = LogManager.getLogger(CustomXMLHandler.class);
	
	private Set<Medicine> medicins;
	private EnumSet<ElementsEnum> withContent;
	
	private ElementsEnum currentElement;
	private MedicineFactory mFactory;
	
	private Medicine currentMedicine;
	private Version currentVersion;
	private Certificate currentCertificate;
	private Pack currentPackage;
	private Dosage currentDosage;
	
	public CustomXMLHandler() {
		medicins = new HashSet<Medicine>();
		withContent = EnumSet.range(ElementsEnum.PHARM, ElementsEnum.FREQUENCY);
		mFactory = new MedicineFactory();
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
		currentElement = ElementsEnum.valueOf(localName.toUpperCase());
		switch (currentElement) {
			case ANTIBIOTIC:
			case VITAMIN:
			case ANALGETIC:
				currentMedicine = mFactory.getMedicine(currentElement);
				for (int i = 0; i < attrs.getLength(); i++) {
					AttributesEnum currentAttribute = AttributesEnum.valueOf(
							attrs.getLocalName(i).toUpperCase());
					String value = attrs.getValue(i);
					switch (currentAttribute) {
						case NAME:
							currentMedicine.setName(value);
							break;
						case CAS:
							currentMedicine.setCas(value);
							break;
						case DRUG_BANK:
							currentMedicine.setDrugBank(value);
							break;
						case RECIPE:
							((Antibiotic)currentMedicine).setRecipe(Boolean.parseBoolean(value));
							break;
						case SOLUTION:
							((Vitamin)currentMedicine).setSolution(value);
							break;
						case NARCOTIC:
							((Analgetic)currentMedicine).setNarcotic(Boolean.parseBoolean(value));
						default:
							break;
					}
				}
				break;
			case VERSION:
				currentVersion = new Version();
				currentVersion.setTradeName(attrs.getValue(0));
				break;
			case PRODUCER:
			case FORM:
			case CERTIFICATE:
			case REGISTRED_BY:
			case REGISTRATION_DATE:
			case EXPIRE_DATE:
			case PACK:
			case QUANTITY:
			case PRICE:
			case DOSAGE:
			case AMOUNT:
			case FREQUENCY:
			default:
				break; // STUB
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
		String content = new String(ch, start, length).trim();
		if (currentElement != null) {
			switch (currentElement) {
				case PHARM:
					currentMedicine.setPharm(content);
					break;
			}
		}
		
		
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
