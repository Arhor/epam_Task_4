package by.epam.task4.service.sax;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	private ElementsEnum currentElement;
	private MedicineFactory mFactory;
	private DateFormat dateFormat;
	
	private Medicine currentMedicine;
	private Version currentVersion;
	private Certificate currentCertificate;
	private Pack currentPack;
	private Dosage currentDosage;
	
	public CustomXMLHandler() {
		medicins = new HashSet<Medicine>();
		mFactory = new MedicineFactory();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
					String name = attrs.getLocalName(i);
					String value = attrs.getValue(i);
					AttributesEnum currentAttribute = AttributesEnum.valueOf(
							name.toUpperCase());
					
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
				LOG.debug("Medicine object created\n");
				break;
			case VERSION:
				currentVersion = new Version();
				currentVersion.setTradeName(attrs.getValue(0)); // because there is only one attribute in <Version> element
				LOG.debug("Version object created\n");
				break;
			case CERTIFICATE:
				currentCertificate = new Certificate();
				LOG.debug("Certificate object created\n");
				break;
			case PACK:
				currentPack = new Pack();
				if (attrs.getLength() > 0) {
					currentPack.setSize(attrs.getValue(0));
				}
				LOG.debug("Pack object created\n");
				break;
			case DOSAGE:
				currentDosage = new Dosage();
				LOG.debug("Dosage object created\n");
				break;
			default:
				break; // STUB
		}
		
		String line = "<" + localName;
		for (int i = 0; i < attrs.getLength(); i++) {
			line += " " + attrs.getLocalName(i) + " = " + attrs.getValue(i);
		}
		line += ">";
		LOG.info(line.trim());
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) {
		currentElement = ElementsEnum.valueOf(localName.toUpperCase());
		switch (currentElement) {
			case ANTIBIOTIC:
			case VITAMIN:
			case ANALGETIC:
				medicins.add(currentMedicine);
				LOG.debug("Medicine object added to collection\n");
				break;
			case VERSION:
				currentMedicine.addVersion(currentVersion);
				LOG.debug("Medicine object added to medicine object\n");
				break;
			case CERTIFICATE:
				currentVersion.setCertificate(currentCertificate);
				LOG.debug("Certificate object added to verion object\n");
				break;
			case PACK:
				currentVersion.addPack(currentPack);
				LOG.debug("Pack object added to version object\n");
				break;
			case DOSAGE:
				currentVersion.setDosage(currentDosage);
			default:
				break;
		}
		
		LOG.info("</" + localName + ">");
	}
	
	@Override
	public void characters(char[] ch, int start, int length) {
		String content = new String(ch, start, length).trim();
		if (currentElement != null && !content.isEmpty()) {
			switch (currentElement) {
				case PHARM:
					currentMedicine.setPharm(content);
					break;
				case PRODUCER:
					currentVersion.setProducer(content);
					break;
				case FORM:
					currentVersion.setForm(content);
					break;
				case REGISTRED_BY:
					currentCertificate.setRegistredBy(content);
					break;
				case REGISTRATION_DATE:
					try {
						currentCertificate.setRegistrationDate(dateFormat.parse(content));
					} catch (ParseException e) {
						LOG.error("Date parsing exception: ", e);
					}
					break;
				case EXPIRE_DATE:
					try {
						currentCertificate.setExpireDate(dateFormat.parse(content));
					} catch (ParseException e) {
						LOG.error("Date parsing exception: ", e);
					}
					break;
				case QUANTITY:
					currentPack.setQuantity(Integer.parseInt(content));
					break;
				case PRICE:
					currentPack.setPrice(Double.parseDouble(content));
					break;
				case AMOUNT:
					currentDosage.setAmount(content);
					break;
				case FREQUENCY:
					currentDosage.setFrequency(content);
					break;
				default:
					break;
			}
		}
		
		
		LOG.info(new String(ch, start, length));
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
