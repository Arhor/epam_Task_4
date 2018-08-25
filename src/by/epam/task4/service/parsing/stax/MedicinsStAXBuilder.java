package by.epam.task4.service.parsing.stax;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task4.model.Analgetic;
import by.epam.task4.model.Antibiotic;
import by.epam.task4.model.Certificate;
import by.epam.task4.model.Dosage;
import by.epam.task4.model.Medicine;
import by.epam.task4.model.Pack;
import by.epam.task4.model.Version;
import by.epam.task4.model.Vitamin;
import by.epam.task4.service.factory.MedicineFactory;
import by.epam.task4.service.parsing.AttributesEnum;
import by.epam.task4.service.parsing.ElementsEnum;
import by.epam.task4.service.parsing.MedicinsAbstractBuilder;

public class MedicinsStAXBuilder extends MedicinsAbstractBuilder{
	
	private static final Logger LOG = LogManager.getLogger(MedicinsStAXBuilder.class);
	
	private XMLInputFactory inputFactory;
	private MedicineFactory mFactory;
	private DateFormat dateFormat;
	
	public MedicinsStAXBuilder() {
		super();
		mFactory = new MedicineFactory();
		medicins = new HashSet<Medicine>();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		inputFactory = XMLInputFactory.newInstance();
	}

	@Override
	public void buildSetMedicins(String xml) {
		XMLStreamReader reader = null;
		FileInputStream fis = null;
        try {
        	fis = new FileInputStream(new File(xml));
        	reader = inputFactory.createXMLStreamReader(fis);
        	while (reader.hasNext()) {
        		int type = reader.next();
        		if (type == XMLStreamConstants.START_ELEMENT) {
        			String name = reader.getLocalName();
        			ElementsEnum currentElement = ElementsEnum.valueOf(name.toUpperCase());
        			switch (currentElement) {
        				case ANTIBIOTIC:
        				case ANALGETIC:
        				case VITAMIN:
        					medicins.add(buildMedicine(reader));
        				default:
        					break;
        			}
        		}
        	}
        } catch (FileNotFoundException e) {
        	LOG.fatal(xml + " file not found", e);
        	throw new RuntimeException();
        } catch (XMLStreamException e) {
        	LOG.error("StAX parsing exception", e);
        } finally {
        	if (reader != null) {
        		try {
        			reader.close();
        		} catch (XMLStreamException e) {
        			LOG.error("XML stream exception", e);
        		}
        	}
        }
	}

	private Medicine buildMedicine(XMLStreamReader reader) throws XMLStreamException {
		
		ElementsEnum currentMedicine = ElementsEnum.valueOf(reader.getLocalName().toUpperCase());
		Medicine medicine = mFactory.getMedicine(currentMedicine);
		
		String name = reader.getAttributeValue(null, AttributesEnum.NAME.getValue());
		String cas = reader.getAttributeValue(null, AttributesEnum.CAS.getValue());
		String drugBank = reader.getAttributeValue(null, AttributesEnum.DRUG_BANK.getValue());
		
		medicine.setName(name);
		medicine.setCas(cas);
		medicine.setDrugBank(drugBank);
		
		switch (currentMedicine) {
			case ANTIBIOTIC:
				boolean recipe = Boolean.parseBoolean(reader.getAttributeValue(null, AttributesEnum.RECIPE.getValue()));
				((Antibiotic) medicine).setRecipe(recipe);
				break;
			case ANALGETIC:
				boolean narcotic = Boolean.parseBoolean(reader.getAttributeValue(null, AttributesEnum.NARCOTIC.getValue()));
				((Analgetic) medicine).setNarcotic(narcotic);
				break;
			case VITAMIN:
				String solution = reader.getAttributeValue(null, AttributesEnum.SOLUTION.getValue());
				((Vitamin) medicine).setSolution(solution);
		default:
			break;
		}
		
		Version currentVersion = null;
		Certificate currentCertificate = null;
		Pack currentPack = null;
		Dosage currentDosage = null;
		
		while (reader.hasNext()) {
			int type = reader.next();
			ElementsEnum currentElement = null;
			if (type == XMLStreamConstants.START_ELEMENT) {
				currentElement = ElementsEnum.valueOf(reader.getLocalName().toUpperCase());
				switch (currentElement) {
					case PHARM:
						medicine.setPharm(getTextContent(reader));
						break;
					case VERSION:
						currentVersion = new Version();
						String tradeName = reader.getAttributeValue(null, AttributesEnum.TRADE_NAME.getValue());
						currentVersion.setTradeName(tradeName);
						break;
					case PRODUCER:
						currentVersion.setProducer(getTextContent(reader));
						break;
					case FORM:
						currentVersion.setForm(getTextContent(reader));
						break;
					case CERTIFICATE:
						currentCertificate = new Certificate();
						break;
					case REGISTRED_BY:
						currentCertificate.setRegistredBy(getTextContent(reader));
						break;
					case REGISTRATION_DATE:
						try {
							Date registrationDate = dateFormat.parse(getTextContent(reader));
							currentCertificate.setRegistrationDate(registrationDate);
						} catch (ParseException e) {
							LOG.error("Date parsing exception", e);
						}
						break;
					case EXPIRE_DATE:
						try {
							Date expireDate = dateFormat.parse(getTextContent(reader));
							currentCertificate.setExpireDate(expireDate);
						} catch (ParseException e) {
							LOG.error("Date parsing exception", e);
						}
						break;
					case PACK:
						currentPack = new Pack();
						String size = reader.getAttributeValue(null, AttributesEnum.SIZE.getValue());
						currentPack.setSize(size);
						break;
					case QUANTITY:
						int quantity = Integer.parseInt(getTextContent(reader));
						currentPack.setQuantity(quantity);
						break;
					case PRICE:
						double price = Double.parseDouble(getTextContent(reader));
						currentPack.setPrice(price);
						break;
					case DOSAGE:
						currentDosage = new Dosage();
						break;
					case AMOUNT:
						currentDosage.setAmount(getTextContent(reader));
						break;
					case FREQUENCY:
						currentDosage.setFrequency(getTextContent(reader));
						break;
					default:
						break;
				}
			} else if (type == XMLStreamConstants.END_ELEMENT) {
				currentElement = ElementsEnum.valueOf(reader.getLocalName().toUpperCase());
				if (currentElement == currentMedicine) {
					break;
				}
				switch (currentElement) {
					case VERSION:
						medicine.addVersion(currentVersion);
						break;
					case CERTIFICATE:
						currentVersion.setCertificate(currentCertificate);
						break;
					case PACK:
						currentVersion.addPack(currentPack);
						break;
					case DOSAGE:
						currentVersion.setDosage(currentDosage);
						break;
					default:
						break;
				}
			}
		}
		
		return medicine;
	}
	
	/**
	 * 
	 * @param reader
	 * @return
	 * @throws XMLStreamException
	 */
	private String getTextContent(XMLStreamReader reader) throws XMLStreamException {
		String content = null;
		if (reader.hasNext()) {
			reader.next();
			content = reader.getText();
		}
		return content;
	}

}
