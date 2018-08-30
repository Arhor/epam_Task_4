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

import by.epam.task4.exception.BuildMedicineException;
import by.epam.task4.exception.MedicineNotPresentedException;
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
	
	private static final Logger LOG = LogManager.getLogger();
	
	private XMLInputFactory inputFactory;
	private MedicineFactory mFactory;
	private DateFormat dateFormat;
	
	private ElementsEnum currentMedicine;
	
	public MedicinsStAXBuilder() {
		super();
		mFactory = new MedicineFactory();
		medicins = new HashSet<Medicine>();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		inputFactory = XMLInputFactory.newInstance();
	}

	@Override
	public boolean buildSetMedicins(String xml) {
		XMLStreamReader reader = null;
		FileInputStream fis = null;
        try {
        	fis = new FileInputStream(new File(xml));
        	reader = inputFactory.createXMLStreamReader(fis);
        	while (reader.hasNext()) {
        		int type = reader.next();
        		if (type == XMLStreamConstants.START_ELEMENT) {
        			String name = reader.getLocalName();
        			currentMedicine = ElementsEnum.valueOf(name.toUpperCase());
        			switch (currentMedicine) {
        				case ANTIBIOTIC:
        				case ANALGETIC:
        				case VITAMIN:
        					medicins.add(buildMedicine(reader));
        				default:
        					break;
        			}
        		}
        	}
        	return true;
        } catch (FileNotFoundException e) {
        	LOG.fatal(xml + " file not found", e);
        	throw new RuntimeException();
        } catch (XMLStreamException e) {
        	LOG.error("StAX parsing exception", e);
        } catch (BuildMedicineException e) {
        	LOG.error("An error occured within building Medicine object", e);
        } finally {
        	if (reader != null) {
        		try {
        			reader.close();
        		} catch (XMLStreamException e) {
        			LOG.error("XML stream exception", e);
        		}
        	}
        }
        return false;
	}

	private Medicine buildMedicine(XMLStreamReader reader)
			throws XMLStreamException, BuildMedicineException {
		
		Medicine medicine;
		try {
			medicine = mFactory.getMedicine(currentMedicine);
		} catch (MedicineNotPresentedException e) {
			LOG.error("Medicine not presented exception", e);
			throw new BuildMedicineException("Medicine not presented", e);
		}
				
		medicine.setName(
				reader.getAttributeValue(null, AttributesEnum.NAME.getValue()));
		medicine.setCas(
				reader.getAttributeValue(null, AttributesEnum.CAS.getValue()));
		medicine.setDrugBank(
				reader.getAttributeValue(
						null, AttributesEnum.DRUG_BANK.getValue()));
		
		switch (currentMedicine) {
			case ANTIBIOTIC:
				boolean recipe = Boolean.parseBoolean(
						reader.getAttributeValue(
								null, AttributesEnum.RECIPE.getValue()));
				((Antibiotic) medicine).setRecipe(recipe);
				break;
			case ANALGETIC:
				boolean narcotic = Boolean.parseBoolean(
						reader.getAttributeValue(
								null, AttributesEnum.NARCOTIC.getValue()));
				((Analgetic) medicine).setNarcotic(narcotic);
				break;
			case VITAMIN:
				String solution = reader.getAttributeValue(
						null, AttributesEnum.SOLUTION.getValue());
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
				currentElement = ElementsEnum.valueOf(
						reader.getLocalName().toUpperCase());
				switch (currentElement) {
					case PHARM:
						medicine.setPharm(geContent(reader));
						break;
					case VERSION:
						currentVersion = new Version();
						String tradeName = reader.getAttributeValue(
								null, AttributesEnum.TRADE_NAME.getValue());
						currentVersion.setTradeName(tradeName);
						break;
					case PRODUCER:
						currentVersion.setProducer(geContent(reader));
						break;
					case FORM:
						currentVersion.setForm(geContent(reader));
						break;
					case CERTIFICATE:
						currentCertificate = new Certificate();
						break;
					case REGISTRED_BY:
						currentCertificate.setRegistredBy(geContent(reader));
						break;
					case REGISTRATION_DATE:
						try {
							Date regDate = dateFormat.parse(geContent(reader));
							currentCertificate.setRegistrationDate(regDate);
						} catch (ParseException e) {
							LOG.error("Date parsing exception", e);
						}
						break;
					case EXPIRE_DATE:
						try {
							Date expDate = dateFormat.parse(geContent(reader));
							currentCertificate.setExpireDate(expDate);
						} catch (ParseException e) {
							LOG.error("Date parsing exception", e);
						}
						break;
					case PACK:
						currentPack = new Pack();
						String size = reader.getAttributeValue(
								null, AttributesEnum.SIZE.getValue());
						currentPack.setSize(size);
						break;
					case QUANTITY:
						int quantity = Integer.parseInt(geContent(reader));
						currentPack.setQuantity(quantity);
						break;
					case PRICE:
						double price = Double.parseDouble(geContent(reader));
						currentPack.setPrice(price);
						break;
					case DOSAGE:
						currentDosage = new Dosage();
						break;
					case AMOUNT:
						currentDosage.setAmount(geContent(reader));
						break;
					case FREQUENCY:
						currentDosage.setFrequency(geContent(reader));
						break;
					default:
						break;
				}
			} else if (type == XMLStreamConstants.END_ELEMENT) {
				currentElement = ElementsEnum.valueOf(
						reader.getLocalName().toUpperCase());
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
	private String geContent(XMLStreamReader reader) throws XMLStreamException {
		String content = null;
		if (reader.hasNext()) {
			reader.next();
			content = reader.getText();
		}
		return content;
	}

}
